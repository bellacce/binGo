package com.wenhui.project.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: wh_shopbox
 * @description: 短信登录表单
 * @author: Mr.Wang
 * @create: 2023-02-08 22:35
 **/
@Data
@ApiModel(value = "SmsLoginForm 对象", description = "短信登录表单Form")
public class SmsLoginForm {

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String phones;

    /**
     * 短信内容
     */
    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String smscode;

    @ApiModelProperty("扫二维码进来的用户，获取到推荐用户id传入")
    private String recommendId;
}
