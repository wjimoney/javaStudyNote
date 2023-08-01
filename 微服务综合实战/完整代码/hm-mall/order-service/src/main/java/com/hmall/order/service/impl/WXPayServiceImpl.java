package com.hmall.order.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.hmall.order.service.WXPayService;
import com.hmall.order.utils.PayConfig;
import org.springframework.stereotype.Service;

import javax.sound.midi.SoundbankResource;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {

    private PayConfig config = new PayConfig();
    @Override
    public Map<String, String> createNative(String out_trade_no, String total_fee, String goods) {


        WXPay wxPay=new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "黑马旅游网");
        data.put("out_trade_no", out_trade_no);
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", total_fee);
        data.put("spbill_create_ip", "127.0.0.1");
        data.put("notify_url", "http://testxiadong.5gzvip.91tunnel.com/pay/callBack");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", goods);

        try {
            Map<String, String> resp = wxPay.unifiedOrder(data);
            System.out.println(resp);
            return resp;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Map<String, String> queryNative(String out_trade_no) {
        PayConfig config=new PayConfig();
        WXPay wxPay=new WXPay(config);

        Map<String,String> data=new HashMap<>();
        data.put("out_trade_no",out_trade_no);

        try{
        Map<String,String> resp = wxPay.orderQuery(data);
        System.out.println(resp);
        return resp;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Map<String, String> closeNative(String out_trade_no) {
        PayConfig config = new PayConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);

        try {
            return wxpay.closeOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
