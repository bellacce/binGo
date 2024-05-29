package com.wenhui.project.web.dto;

import com.wenhui.project.web.vo.PayDetailsVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResultDto {

    @ApiModelProperty(value = "返回类型 1链接 2图片，3、代码")
    public Integer resultType;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "支付信息")
    private String payInfo;



    //    @ApiModelProperty(value = "支付信息")
//    private PayDetailsVo payDetailsVo;



}
