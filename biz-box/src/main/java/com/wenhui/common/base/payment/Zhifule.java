package com.wenhui.common.base.payment;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wenhui.common.base.enums.PayTyprEnum;
import com.wenhui.core.base.utils.CustomHttpClient;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.integration.pay.alipay.AlipayServiceCall;
import com.wenhui.integration.pay.alipay.AlipayServiceCallConstant;
import com.wenhui.integration.pay.alipay.AlipayServiceResponse;
import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.biz.service.OrdersPayRequestLogService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.OrderPaymentCallDto;
import com.wenhui.project.web.dto.PayParamDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class Zhifule extends Payment{


    @Override
    public PaymentResultBo pay(PayParamDto payParamDto, LogSaveService logSaveService) {
        OrderPaymentCallDto orderPaymentCallDto = new OrderPaymentCallDto();
//        payParamDto.setUrl("http://api.hvzhan.cn/createOrder");
        payParamDto.setUrl(payParamDto.getChannelUrl().trim());
        PaymentResultBo paymentResultBo = new PaymentResultBo();
        //商户订单号
        orderPaymentCallDto.setPayId(payParamDto.getOrderNo());
        //支付类型 type=1微信，type=2支付宝
        switch (payParamDto.getPayType()) {
            case "alipay":
                orderPaymentCallDto.setType(PayTyprEnum.ALIPAY.getCode());
                break;
            case "wepay":
                orderPaymentCallDto.setType(PayTyprEnum.WXPAY.getCode());
                break;
            default:
                throw new BusinessException(String.valueOf(ErrorCode.METHOD_NOT_ALLOWED), "支付方式错误");
        }
        AlipayServiceCallConstant.URL=payParamDto.getUrl();
        //私钥
        //支付方式1是跳转到支付页面，2是返回json数据

        BigDecimal price = payParamDto.getPrice();
        price = price.stripTrailingZeros();
        int ishtml=1;
        try {
            orderPaymentCallDto.setNotifyUrl(payParamDto.getNotifyUrl())
                    .setUid(Integer.parseInt(payParamDto.getUid()))
                    .setReturnUrl(payParamDto.getReturnUrl())
                    .setPrice(price)
                    .setParam("JUCHUANGSOFT")
                    .setIsHtml(ishtml);
            String sign = orderPaymentCallDto.getPayId() + orderPaymentCallDto.getParam() + orderPaymentCallDto.getType() + price + payParamDto.getPrivateKey();
            //签名 传递密钥即可
            String md5 = DigestUtils.md5Hex(sign);
            orderPaymentCallDto.setSign(md5);
           /* JSONObject jsonObject = CustomHttpClient.doJsonPost(AlipayServiceCallConstant.URL, orderPaymentCallDto, new TypeReference<JSONObject>() {
            });
            if (Objects.nonNull(jsonObject.getInteger("code"))&&jsonObject.getInteger("code")>0){
                paymentResultBo.setJson(jsonObject.getJSONObject("data"));
            }
            log.info(JSON.toJSONString(jsonObject));*/
            HttpResponse execute = HttpRequest.post(AlipayServiceCallConstant.URL).body(JSON.toJSONString(orderPaymentCallDto)).execute();
            String body1 = execute.body();
            if(StringUtils.isEmpty(body1)||body1.contains("code")){
                JSONObject jsonObject = JSONObject.parseObject(body1);
                paymentResultBo.setResquestInfo(jsonObject.getString("msg"));
                throw new BusinessException("5000019","支付错误，支付配置有问题");
            }
            if(body1.contains("http")){
                String substring = body1.substring(body1.indexOf("'") + 1, body1.lastIndexOf("'"));
                paymentResultBo.setResquestInfo(substring);
            }

            log.info("支付乐返回结果：{}",body1);
//            paymentResultBo.setUrl(substring);
            paymentResultBo.setCode(10000);
            String s = JSON.toJSONString(orderPaymentCallDto);
            paymentResultBo.setJson(JSONObject.parseObject(s));
            paymentResultBo.setResultType(1);

//            paymentResultBo.setJson(JSONObject.parseObject("{\"date\":1676525787,\"uid\":3697,\"payType\":1,\"orderId\":\"202302161302276186\",\"price\":0.01,\"outTradeNo\":\"202302161302276186\",\"reallyPrice\":\"0.01\",\"isAuto\":1,\"payUrl\":\"wxp://f2f0TVzKge5GvD0giekm1o-I7un5yFFd3YoPYg--0vcKNx9wW_yD6UJEexeaRuH6kQne\",\"payId\":\"20233616013624938\",\"state\":0,\"timeOut\":3}"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            logSaveService.saveLog(new OrdersPayRequestLog(AlipayServiceCallConstant.URL, JSONObject.toJSONString(orderPaymentCallDto), JSONObject.toJSONString(paymentResultBo), 1));
        }
        if (Objects.nonNull(paymentResultBo.getCode()) && paymentResultBo.getCode() == -1) {
            log.info("支付失败," + paymentResultBo.getResquestInfo());
            throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "拉起支付失败，请重新购买！");
        }
        if (paymentResultBo.getJson().isEmpty()) {
            throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
        }
        return paymentResultBo;
    }
}
