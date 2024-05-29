package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryVo {

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "快递公司名称")
    private String expressName;

    @ApiModelProperty(value = "物流单号")
    private String expressNo;

    @ApiModelProperty(value = "运费")
    private BigDecimal logisticsFee;

}
