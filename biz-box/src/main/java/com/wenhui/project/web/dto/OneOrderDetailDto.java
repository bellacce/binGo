package com.wenhui.project.web.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class OneOrderDetailDto {

    /**
     * 数据主键
     */
    @ApiModelProperty("数据主键")
    private Integer orderId;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 支付总金额
     */
    @ApiModelProperty("支付总金额")
    private BigDecimal orderAmountTotal;

    /**
     * 订单创建时间 下单时间
     */
    @ApiModelProperty("下单时间")
    private Date createTime;

    /**
     * 1、待开箱，2、待提货,3、待发货，4、已发货
     */
    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单状态")
    private String  orderStatusName;

    @ApiModelProperty("订单收获地址")
    private AddressOneOrder userAddressDto;
    @ApiModelProperty("订单商品列表")
    private List<OrderGoodsListDto> listDtos;

    public Integer getOrderStatus() {
        switch (orderStatus){
            case 1:
                this.orderStatusName="待开箱";
                break;
            case 2:
                this.orderStatusName="待提货";
                break;
            case 3:
                this.orderStatusName="待发货";
                break;
            case 4:
                this.orderStatusName="已发货";
                break;
        }

        return orderStatus;
    }

    public String getCreateTime() {
        if (this.createTime!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(this.createTime);
        }
        return createTime.toString();
    }
}
