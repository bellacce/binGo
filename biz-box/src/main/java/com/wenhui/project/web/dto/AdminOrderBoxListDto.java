package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminOrderBoxListDto {
    @ApiModelProperty("盲盒id")
    private String orderDetailId;

    @ApiModelProperty("盲盒名称")
    private String productName;

    @ApiModelProperty("盲盒封面")
    private String productThumb;

    @ApiModelProperty("购买数量")
    private Integer number;

    @ApiModelProperty("盲盒价格")
    private BigDecimal productPrice;


    @ApiModelProperty("订单表id")
    private Integer orderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("第三方支付流水号")
    private String outTradeNo;

    @ApiModelProperty("购买用户id")
    private String userId;

    @ApiModelProperty("支付总金额")
    private BigDecimal orderAmountTotal;
    @ApiModelProperty("商品总价")
    private BigDecimal productAmountTotal;
    @ApiModelProperty("付款时间")
    private Date paymentTime;

    @ApiModelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("订单状态名册")
    private String orderStatusName;

    @ApiModelProperty("订单支付渠道")
    private String payChannelName;

    @ApiModelProperty("订单支付方式名称")
    private String payName;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("修改时间")
    private Date updatedAt;
}
