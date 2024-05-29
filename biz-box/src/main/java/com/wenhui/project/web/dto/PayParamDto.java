package com.wenhui.project.web.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayParamDto {


    @ApiModelProperty(value = "url地址")
    private String url;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "商户id")
    private String uid;
    @ApiModelProperty(value = "私钥")
    private String privateKey;
    @ApiModelProperty(value = "公钥")
    private String publicKey;
    @ApiModelProperty(value = "同步通知地址")
    private String returnUrl;
    @ApiModelProperty(value = "异步通知地址")
    private String notifyUrl;
    @ApiModelProperty(value = "支付方式")
    private String payType;
    @ApiModelProperty(value = "支付方式ID")
    private Integer payTypeID;
    @ApiModelProperty(value = "商品名称")
    private String tradeName;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "渠道URL")
    private String channelUrl;

    @ApiModelProperty(value = "用户发起支付客户端ip")
    private String clientIp;
}
