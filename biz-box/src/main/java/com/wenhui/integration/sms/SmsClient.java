package com.wenhui.integration.sms;

import com.wenhui.core.core.web.CommonRestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SmsClient {

    @PostMapping(value = "/services/sms/sendByChannelCode")
    CommonRestResult sendByChannelCode(@RequestBody ChannelSendForm form);

    @PostMapping(value = "/services/sms/sendBySignCode")
    CommonRestResult sendBySignCode(@RequestBody SignSendForm form);

}
