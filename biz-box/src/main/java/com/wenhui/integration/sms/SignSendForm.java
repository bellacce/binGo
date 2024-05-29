package com.wenhui.integration.sms;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SignSendForm extends BaseSmsForm {

    @NotBlank(message = "签名代码")
    private String signCode;

    @NotBlank(message = "手机号")
    private String phones;

    @NotBlank(message = "短信内容")
    private String content;

}
