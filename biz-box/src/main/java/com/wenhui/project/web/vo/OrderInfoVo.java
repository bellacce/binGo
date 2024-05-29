package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderInfoVo {

    @ApiModelProperty(value = "支付方式的id")
    private String paymentMethodId;

    @ApiModelProperty(value = "支付渠道的类型")
    private String channelId;

    @ApiModelProperty(value = "商品类型，1是实物，2是盲盒")
    private Integer productType;

    @ApiModelProperty(value = "物流地址id")
    private Integer addressId;

    @ApiModelProperty(value = "商品Id")
    private String productId;







}
