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


}
