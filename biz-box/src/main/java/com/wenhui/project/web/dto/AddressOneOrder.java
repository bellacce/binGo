package com.wenhui.project.web.dto;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class AddressOneOrder {
    /**
     * 自动编号
     */
    @ApiModelProperty("自动编号")
    private Integer orderlogisticsId;
    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    private Integer orderId;
    @ApiModelProperty("物流信息")
    private String expressNo;
    @ApiModelProperty("物流发货公司")
    private String logisticsType;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    private String consigneeRealname;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String consigneeTelphone;
    /**
     * 收货地址
     */
    @ApiModelProperty("收货地址")
    private String consigneeAddress;

    /**
     * 物流状态
     */
    @ApiModelProperty("物流状态")
    private String orderlogisticsStatus;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;

    public String getCreatedAt() {
        if (this.createdAt!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(this.createdAt);
        }
        return createdAt.toString();
    }
}
