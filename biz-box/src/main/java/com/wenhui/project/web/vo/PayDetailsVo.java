package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayDetailsVo {
    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "商户id")
    private String uid;
    @ApiModelProperty(value = "支付签名")
    private String sign;
    @ApiModelProperty(value = "同步通知地址")
    private String returnUrl;
    @ApiModelProperty(value = "异步通知地址")
    private String notifyUrl;
    @ApiModelProperty(value = "支付方式")
    private String payType;
    @ApiModelProperty(value = "商品名称")
    private String tradeName;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;
}
