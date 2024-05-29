package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单物流表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_logistics")
public class OrderLogistics extends Model<OrderLogistics> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "orderlogistics_id",  type = IdType.AUTO)
    private Integer orderlogisticsId;
    /**
     * 订单编号
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 物流单号
     */
    @TableField("express_no")
    private String expressNo;
    /**
     * 收货人姓名
     */
    @TableField("consignee_realname")
    private String consigneeRealname;
    /**
     * 联系电话
     */
    @TableField("consignee_telphone")
    private String consigneeTelphone;
    /**
     * 收货地址
     */
    @TableField("consignee_address")
    private String consigneeAddress;
    /**
     * 物流方式
     */
    @TableField("logistics_type")
    private String logisticsType;
    /**
     * 物流发货运费
     */
    @TableField("logistics_fee")
    private BigDecimal logisticsFee;
    /**
     * 物流状态
     */
    @TableField("orderlogistics_status")
    private String orderlogisticsStatus;
    /**
     * 物流最后状态描述
     */
    @TableField("logistics_result_last")
    private String logisticsResultLast;
    /**
     * 物流描述
     */
    @TableField("logistics_result")
    private String logisticsResult;
    /**
     * 发货时间
     */
    @TableField("logistics_create_time")
    private Date logisticsCreateTime;
    /**
     * 物流更新时间
     */
    @TableField("logistics_update_time")
    private Date logisticsUpdateTime;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 0正常1删除
     */
    @TableField("delete_flag")
    private Integer deleteFlag;


    @Override
    protected Serializable pkVal() {
        return this.orderlogisticsId;
    }

}
