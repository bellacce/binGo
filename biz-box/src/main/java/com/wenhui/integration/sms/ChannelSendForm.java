package com.wenhui.integration.sms;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ChannelSendForm extends BaseSmsForm {

    /**
     * 通道代码
     */
    @NotBlank(message = "通道代码不能为空")
    private String channelCode;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phones;

    /**
     * 短信内容
     */
    @NotBlank(message = "短信内容不能为空")
    private String content;

}
