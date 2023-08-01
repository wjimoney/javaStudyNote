package com.hmall.order.listener;

import com.hmall.order.service.IOrderService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrderListener {

    @Autowired
    private IOrderService orderService;
    //处理过期订单
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "hmall.delay.queue", durable = "true"),
            exchange = @Exchange(name = "hmall.delay.direct", delayed = "true"),
            key = "delay"
    ))
    public void listenDelayExchange(String msg) {
        if(!StringUtils.isEmpty(msg)){
            orderService.dealExpireOrder(Long.parseLong(msg));
        }
    }
}
