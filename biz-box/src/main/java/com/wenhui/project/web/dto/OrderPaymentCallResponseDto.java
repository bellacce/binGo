package com.wenhui.project.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 订单支付调用响应Dto
 @create 2023/2/10
*/
@Data
@Accessors(chain = true)
public class OrderPaymentCallResponseDto {
    /**
     * 商户ID
     */
    private Integer uid;
    /**
     * 商户订单号
     */
    private String payId;
    /**
     * 支付方式
     */
    private Integer payType;
    /**
     * 订单流水号
     */
    private String outTradeNo;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 商品金额
     */
    private BigDecimal price;
    /**
     * 商品金额
     */
    private String reallyPrice;

    /**
     * 时间
     */
    private Long date;
    /**
     * 是否自动
     */
    private Integer isAuto;
    /**
     * 支付码地址
     */
    private String payUrl;
    /**
     * 支付状态
     */
    private Integer state;

    /**
     * 超时时间
     */
    private Integer timeOut;


}
