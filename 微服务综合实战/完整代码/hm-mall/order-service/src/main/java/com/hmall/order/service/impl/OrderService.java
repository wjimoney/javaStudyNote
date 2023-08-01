package com.hmall.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.common.client.ItemClient;
import com.hmall.common.client.UserClient;
import com.hmall.common.dto.Address;
import com.hmall.common.dto.Item;
import com.hmall.order.mapper.OrderMapper;
import com.hmall.order.pojo.Order;
import com.hmall.order.pojo.OrderDetail;
import com.hmall.order.pojo.OrderLogistics;
import com.hmall.order.pojo.RequestParams;
import com.hmall.order.service.IOrderService;
import com.hmall.order.service.WXPayService;
import com.hmall.order.utils.PayUtil;
import com.hmall.order.utils.UserHolder;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    // private IdWorker idWorker = new IdWorker(10L, 10L, 0L);

    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private OrderDetailService detailService;
    @Autowired
    private OrderLogisticsService logisticsService;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    @Override
    public Order createOrder(RequestParams requestParams) {

        //0.微信支付生成
        //暂时固定01业务号
        String outTradeNo = PayUtil.getId("01");  //生成订单号

        //调用微信支付SDK,生成微信支付二维码连接
        Map<String, String> aNative = wxPayService.createNative(outTradeNo,String.valueOf(1),
                requestParams.getItemId().toString());
        if (!("SUCCESS".equals(aNative.get("return_code")) && "SUCCESS".equals(aNative.get("result_code")))) {
            //如果微信支付SDK 生成订单失败, 直接返回订单创建失败
            throw new RuntimeException("微信支付二维码生成异常");
        }

        Order order = new Order();
        order.setTradeNo(outTradeNo);
        order.setWxCodeUrl(aNative.get("code_url"));

        // 1.查询商品
        Item item = itemClient.queryItemById(requestParams.getItemId());
        // 2.基于商品价格、购买数量计算商品总价：totalFee
        long totalFee = item.getPrice() * requestParams.getNum();
        order.setTotalFee(totalFee);
        order.setPaymentType(requestParams.getPaymentType());
        order.setUserId(UserHolder.getUser());
        order.setStatus(1);
        // 3.将Order写入数据库tb_order表中
        this.save(order);

        // 4.将商品信息、orderId信息封装为OrderDetail对象，写入tb_order_detail表
        OrderDetail detail = new OrderDetail();
        detail.setName(item.getName());
        detail.setSpec(item.getSpec());
        detail.setPrice(item.getPrice());
        detail.setNum(requestParams.getNum());
        detail.setItemId(item.getId());
        detail.setImage(item.getImage());
        detail.setOrderId(order.getId());
        detailService.save(detail);


        Long addressId = requestParams.getAddressId();
        // 5.根据addressId查询user-service服务，获取地址信息
        Address address = userClient.findAddressById(addressId);
        // 6.将地址封装为OrderLogistics对象，写入tb_order_logistics表
        OrderLogistics orderLogistics = new OrderLogistics();
        BeanUtils.copyProperties(address, orderLogistics);
        orderLogistics.setOrderId(order.getId());
        logisticsService.save(orderLogistics);

        // 7.扣减库存
        try {
            itemClient.updateStock(requestParams.getItemId(), -requestParams.getNum());
        } catch (Exception e) {
            throw new RuntimeException("库存不足！");
        }

        // 1.延迟消息，30分钟之后处理订单，如果没有支付就取消订单
        Message message = MessageBuilder
                .withBody(order.getId().toString().getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay", 1000 * 60 *30)
                .build();
        // 2.准备CorrelationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.send("hmall.delay.direct","delay",message,correlationData);

        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealExpireOrder(long orderId) {
        Order order = this.getById(orderId);
        if(order == null){
            throw new RuntimeException("订单不存在！");
        }

        if(!order.getStatus().equals(1)){
            throw new RuntimeException("订单状态不正确！");
        }

        OrderDetail detail = detailService.getOne(Wrappers.<OrderDetail>lambdaQuery().eq(OrderDetail::getOrderId, orderId));
        Integer num = detail.getNum();
        Long itemId = detail.getItemId();

        itemClient.updateStock(itemId, num);

        order.setCloseTime(new Date());
        order.setStatus(5);
        updateById(order);

        //关闭微信订单
        wxPayService.closeNative(order.getTradeNo());

    }
}
