package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminBoxOrderDetailVo {

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "奖品名称")
    private String goodsName;

    @ApiModelProperty(value = "稀有度")
    private String tag;

    @ApiModelProperty(value = "商品价格")
    private String orderTotal;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
}
