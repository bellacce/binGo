package com.wenhui.integration.sms;

import com.wenhui.core.core.web.CommonRestResult;
import org.springframework.stereotype.Component;


@Component
public class SmsClientFallback implements SmsClient {

    @Override
    public CommonRestResult sendByChannelCode(ChannelSendForm form) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonRestResult sendBySignCode(SignSendForm form) {
        // TODO Auto-generated method stub
        return null;
    }

}
