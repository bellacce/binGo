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
 * 邮费订单
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_freight_order")
public class StoreFreightOrder extends Model<StoreFreightOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
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
     * 关联订单id
     */
    @TableField("related_id")
    private Integer relatedId;
    /**
     * 规则ID： 1、盲盒
     */
    @TableField("rule_type")
    private Integer ruleType;
    /**
     * 订单状态[1、未付款，2、已付款]
     */
    private String status;
    /**
     * 支付金额
     */
    private BigDecimal amount;
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
     * 收货地址
     */
    private String address;
    /**
     * 付款时间
     */
    @TableField("payment_time")
    private Date paymentTime;
    /**
     * 订单创建时间
     */
    @TableField("create_time")
    private Date createTime;
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


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
