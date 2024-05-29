package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 滚动已开到的商品列表
 @create 2023/4/13
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderRollListVo {

    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "秒数")
    private String second;
    @ApiModelProperty(value = "时间")
    private Date time;
    @ApiModelProperty(value = "商品名称")
    private String name;
}
