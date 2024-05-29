package com.wenhui.common.base.payment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.integration.pay.alipay.AlipayServiceCallConstant;
import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.PayParamDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZfbPay extends Payment{
    @Override
    public PaymentResultBo pay(PayParamDto payParamDto, LogSaveService logSaveService) {
        // 设置请求参数
//        String serverUrl = "https://openapi.alipay.com/gateway.do";
//        String serverUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
        String serverUrl = payParamDto.getChannelUrl().trim();
        String appId = payParamDto.getUid();
        String privateKey = payParamDto.getPrivateKey();
        String format = "json";
        String charset = "utf-8";
        String alipayPublicKey = payParamDto.getPublicKey();
        String signType = "RSA2";
        PaymentResultBo paymentResultBo = new PaymentResultBo();
        JSONObject goodsDetail = new JSONObject();
       try{
           // 创建AlipayClient对象
           AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
           // 创建支付请求对象
           AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
           alipayRequest.setReturnUrl(payParamDto.getReturnUrl());
           alipayRequest.setNotifyUrl(payParamDto.getNotifyUrl());
           // 商品明细信息，按需传入
           goodsDetail.put("out_trade_no",payParamDto.getOrderNo());
           goodsDetail.put("total_amount",payParamDto.getPrice());
           goodsDetail.put("subject",payParamDto.getTradeName());
           goodsDetail.put("body",payParamDto.getTradeName());
           goodsDetail.put("product_code","QUICK_WAP_PAY");
           alipayRequest.setBizContent(goodsDetail.toString());
           AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
           if(response.isSuccess()){
               paymentResultBo.setJson(goodsDetail);
               paymentResultBo.setResultType(3);
               paymentResultBo.setResquestInfo(response.getBody());
           } else {
               throw new BusinessException("5000021","拉取支付宝支付失败");
           }
       }catch (Exception e){
                e.printStackTrace();
       }finally {
           logSaveService.saveLog(new OrdersPayRequestLog(serverUrl, JSONObject.toJSONString(goodsDetail), JSONObject.toJSONString(paymentResultBo), 1));
       }
        return paymentResultBo;
    }
}
