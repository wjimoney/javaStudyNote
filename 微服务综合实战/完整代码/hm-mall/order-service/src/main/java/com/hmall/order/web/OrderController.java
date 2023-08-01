package com.hmall.order.web;

import com.hmall.order.pojo.Order;
import com.hmall.order.pojo.RequestParams;
import com.hmall.order.service.IOrderService;
import com.hmall.order.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private IOrderService orderService;

   @GetMapping("{id}")
   public Order queryOrderById(@PathVariable("id") Long orderId) {
      Long userId = UserHolder.getUser();
      System.err.println(userId);
      return orderService.getById(orderId);
   }

   @PostMapping
   public Order createOrder(@RequestBody RequestParams requestParams){
      return orderService.createOrder(requestParams);
   }

   @GetMapping("/status/{id}")
   public Integer queryStatusById(@PathVariable("id") long orderId){
      //目前只支持微信支付
      Order order = orderService.getById(orderId);
      if(order == null){
         throw new RuntimeException("订单未找到");
      }
      return order.getStatus();
   }
}
