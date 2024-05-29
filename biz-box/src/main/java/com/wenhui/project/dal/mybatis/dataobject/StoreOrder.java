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
 * 用户订单
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_order")
public class StoreOrder extends Model<StoreOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
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
     * 订单状态[1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭]
     */
    private String status;
    /**
     * 实际支付金额
     */
    private BigDecimal amount;
    /**
     * 订单原金额
     */
    @TableField("raw_amount")
    private BigDecimal rawAmount;
    /**
     * 收货人
     */
    private String contact;
    /**
     * 手机号码
     */
    private String moblie;
    /**
     * 赠送的成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;
    /**
     * 赠送的积分
     */
    @TableField("gift_point")
    private Integer giftPoint;
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
     * 交易关闭时间
     */
    @TableField("close_time")
    private Date closeTime;
    /**
     * 交易完成时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 付款时间
     */
    @TableField("payment_time")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @TableField("consign_time")
    private Date consignTime;
    /**
     * 订单创建时间
     */
    @TableField("create_time")
    private Date createTime;
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
    /**
     * 订单类型： 1、普通订单 2、盲盒订单
     */
    @TableField("order_type")
    private Integer orderType;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
