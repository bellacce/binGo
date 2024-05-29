package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 管理员列表Vo
 @create 2023/3/4
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserBoxListVo {

    @ApiModelProperty("ID")
    private String user_id;
    @ApiModelProperty("头像")
    private String user_icon;
    @ApiModelProperty("用户名")
    private String user_name;
    @ApiModelProperty("用户类型")
    private String user_type;
    @ApiModelProperty("幸运值")
    private String user_lucky;
    @ApiModelProperty("余额")
    private String user_bean;
    @ApiModelProperty("总收益")
    private BigDecimal rebate;
}
