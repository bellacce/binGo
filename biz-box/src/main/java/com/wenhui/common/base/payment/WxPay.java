package com.wenhui.common.base.payment;

import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.PayParamDto;

import java.util.Map;

public class WxPay extends Payment{

    @Override
    public PaymentResultBo pay(PayParamDto payParamDto, LogSaveService logSaveService) {
        //微信支付url
        String channelUrl = payParamDto.getChannelUrl();
        

        return null;
    }
}
