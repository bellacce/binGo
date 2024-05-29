package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: wh_shopbox
 * @description: 支付结果异步回调接口
 * @author: Mr.Wang
 * @create: 2023-02-19 18:41
 **/
@Data
public class OrderPaymentStatusDto {

    @ApiModelProperty(value = "商户订单号")
    private String payId;

    @ApiModelProperty(value = "创建订单的时候传入的参数")
    private String param;

    @ApiModelProperty(value = "支付方式 ：微信支付为1 支付宝支付为2")
    private Integer type;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal price;

    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal reallyPrice;

    @ApiModelProperty(value = "校验签名，计算方式 = md5(payId + param + type + price + reallyPrice + 通讯密钥)")
    private String sign;
}
