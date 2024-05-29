package com.wenhui.project.web.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayListPayListVo {

    @ApiModelProperty(value = "支付方式id")
    private Integer id;
    /**
     * 支付方式名称
     */
    @ApiModelProperty(value = "支付方式名称")
    private String payName;
    /**
     * 支付方式ID
     */
    @ApiModelProperty(value = "支付方式id")
    private String payId;
    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;
    /**
     * 支付方式 0开启1关闭
     */
    @ApiModelProperty(value = "支付方式")
    private Integer payState;

    @ApiModelProperty(value = "支付费率")
    private BigDecimal payRate;

}
