package com.wenhui.project.web.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminOrderBoxVo {
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("支付总金额")
    private BigDecimal orderAmountTotal;

    @ApiModelProperty("商品总价")
    private BigDecimal productAmountTotal;
    @ApiModelProperty("订单状态")
    private String orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("付款时间")
    private Date paymentTime;

    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("订单支付渠道")
    private String payChannelName;

    @ApiModelProperty("订单支付方式名称")
    private String payName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("修改时间")
    private Date updatedAt;

    public JSONArray getProductThumb() {
        JSONArray jsonArray = JSON.parseArray(productThumb);
        return jsonArray;
    }
}
