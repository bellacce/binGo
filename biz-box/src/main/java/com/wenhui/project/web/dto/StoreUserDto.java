package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 添加用户Dto
 @create 2023/3/4
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StoreUserDto {

    private Integer uid;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @NotEmpty(message = "用户名称不能为空")
    private String nickname;
    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String mobile;
    /**
     * 账户密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 权限列表
     */
    @NotEmpty(message = "权限列表不能为空")
    @ApiModelProperty("权限列表不能为空")
    private String userRole;
}
