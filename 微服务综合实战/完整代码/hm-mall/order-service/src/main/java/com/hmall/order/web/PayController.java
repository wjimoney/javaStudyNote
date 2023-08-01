package com.hmall.order.web;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.wxpay.sdk.WXPayUtil;
import com.hmall.order.pojo.Order;
import com.hmall.order.pojo.RequestParams;
import com.hmall.order.service.IOrderService;
import com.hmall.order.service.WXPayService;
import com.hmall.order.utils.PayUtil;
import com.hmall.order.utils.StreamUtils;
import com.hmall.order.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/url/{type}/{id}")
    public String queryOrderById(@PathVariable("type") long type, @PathVariable("id") long orderId) {
        if (type == 2) {
            //目前只支持微信支付
            Order order = orderService.getById(orderId);
            if (order == null) {
                throw new RuntimeException("订单未找到");
            }
            return order.getWxCodeUrl();
        }
        return null;
    }


    @GetMapping("/status/{id}")
    public String queryStatusById(@PathVariable("id") long orderId) {
        Order order = orderService.getById(orderId);
        if (order == null) {
            throw new RuntimeException("订单未找到");
        }
        Map<String, String> map = wxPayService.queryNative(order.getTradeNo());
        if (map == null) {
            return "3";
        }

        //支付成功
        if (map.get("trade_state").equals("SUCCESS")) {
            return "2";
        }

        //其他 - 返回未支付
        return "1";
    }

    //回调接口
    @RequestMapping("callBack")
    public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("接口已被调用");
        ServletInputStream inputStream = request.getInputStream();
        String notifyXml = StreamUtils.inputStream2String(inputStream, "utf-8");
        System.out.println(notifyXml);

        // 解析返回结果
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyXml);
        // 判断支付是否成功
        if ("SUCCESS".equals(notifyMap.get("result_code"))) {
            //编写自己的实现逻辑
            // 支付成功：给微信发送我已接收通知的响应
            // 创建响应对象
            String out_trade_no = notifyMap.get("out_trade_no");
            //更新支付状态为已支付
            orderService.update(Wrappers.<Order>lambdaUpdate().set(Order::getStatus,"2")
                    .eq(Order::getTradeNo,out_trade_no));

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("return_code", "SUCCESS");
            returnMap.put("return_msg", "OK");
            String returnXml = WXPayUtil.mapToXml(returnMap);
            response.setContentType("text/xml");
            System.out.println("支付成功");
            return returnXml;
        }
        // 创建响应对象：微信接收到校验失败的结果后，会反复的调用当前回调函数
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", "FAIL");
        returnMap.put("return_msg", "");
        String returnXml = WXPayUtil.mapToXml(returnMap);
        response.setContentType("text/xml");
        System.out.println("校验失败");
        return returnXml;
    }

}
