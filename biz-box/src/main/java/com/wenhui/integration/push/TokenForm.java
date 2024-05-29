package com.wenhui.integration.push;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class TokenForm {

    private String appCode;

    @NotBlank(message = "马甲不能位空")
    private String shadow;

    @NotBlank(message = "openId不能为空")
    private String openId;

    @NotBlank(message = "deviceToken不能位空")
    private String deviceToken;

}
