package com.wenhui.project.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: wh_shopbox
 * @description: 用户登录表单
 * @author: Mr.Wang
 * @create: 2023-02-08 22:35
 **/
@Data
@ApiModel(value = "UserLoginForm 对象", description = "用户登录表单Form")
public class UserLoginForm {

    /**
     * 账号
     */
    @NotEmpty(message = "请输入密码账号")
    @ApiModelProperty("账号")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "请输入密码")
    @ApiModelProperty("密码")
    private String password;

}
