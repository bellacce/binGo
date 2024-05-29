package com.wenhui.project.web.dto;


import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrdersListDto {

    @ApiModelProperty(value = "id")
    private Integer orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 订单类型： 1、普通订单 2、盲盒订单
     */
    @ApiModelProperty(value = "订单类型")
    private Integer storeOrder;

    /**
     * 1、待开箱，2、待提货,3、待发货，4、已发货
     */
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;
    @ApiModelProperty(value = "按钮文字")
    private String buttonName;
    @ApiModelProperty(value = "订单右上角的字")
    private String orderTitle;

    @ApiModelProperty(value = "订单内的商品信息")
    private List<OrderGoodsListDto> orderGoodsListDto;


}
