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
 * 退货订单
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_order_refund")
public class StoreOrderRefund extends Model<StoreOrderRefund> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 订单状态[1、待处理，2、处理中，3、处理完成]
     */
    private String status;
    /**
     * 收货人
     */
    private String contact;
    /**
     * 手机号码
     */
    private String moblie;
    /**
     * 物流名称
     */
    @TableField("shipping_name")
    private String shippingName;
    /**
     * 物流单号
     */
    @TableField("shipping_code")
    private String shippingCode;
    /**
     * 邮费
     */
    @TableField("post_fee")
    private BigDecimal postFee;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
