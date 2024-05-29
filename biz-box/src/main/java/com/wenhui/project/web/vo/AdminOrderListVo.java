package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 后台查看用户盲盒订单列表Vo
 @create 2023/3/4
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminOrderListVo {

    @ApiModelProperty("商品原价总计")
    private BigDecimal user_box_before;
    @ApiModelProperty("盲盒购买花费总计")
    private BigDecimal user_box_pay;
    @ApiModelProperty("订单数")
    private Integer order_sum;
    @ApiModelProperty("盲盒开出的奖品数")
    private Integer user_box_get;
    @ApiModelProperty("订单列表")
    private List<Box> box_order;

    @Data
    public class Box {
        @ApiModelProperty("盲盒类型")
        private String box_name;
        @ApiModelProperty("商品原价")
        private BigDecimal box_before;
        @ApiModelProperty("购买盲盒花费")
        private BigDecimal box_price;
        @ApiModelProperty("盲盒所得")
        private String box_prize;
        @ApiModelProperty("订单数")
        private Integer order_sum;
        @ApiModelProperty("用户收益")
        private BigDecimal userRebate;
        @ApiModelProperty("推荐用户收益")
        private BigDecimal userRecommendRebate;
        @ApiModelProperty("平台收益")
        private BigDecimal terraceRebate;
    }
}
