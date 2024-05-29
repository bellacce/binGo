package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: wh_shopbox
 * @description: 获取运营数据Vo
 * @author: Mr.Wang
 * @create: 2023-03-02 23:56
 **/
@Data
public class GetOperateDataVo {

    @ApiModelProperty("兑换盈利")
    private BigDecimal duihuan;

    @ApiModelProperty("分解盈利")
    private BigDecimal fenjie;

    @ApiModelProperty("提取盈利")
    private BigDecimal tiqu;

    @ApiModelProperty("实物订单")
    private Integer box_sale;

    @ApiModelProperty("实物订单")
    private Integer zhihuan;


}
