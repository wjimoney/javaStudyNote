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


}
