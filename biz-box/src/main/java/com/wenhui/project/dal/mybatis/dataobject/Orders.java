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
 * 用户订单总表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;
    /**
     * 订单类型： 1、普通订单 2、盲盒订单
     */
    @TableField("store_order")
    private Integer storeOrder;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 支付总金额
     */
    @TableField("order_amount_total")
    private BigDecimal orderAmountTotal;
    /**
     * 商品总价 
     */
    @TableField("product_amount_total")
    private BigDecimal productAmountTotal;
    /**
     * 物理单号
     */
    @TableField("shipping_code")
    private String shippingCode;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货地址编号 
     */
    @TableField("address_id")
    private Integer addressId;
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
     * 订单创建时间 下单时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 运费金额 
     */
    @TableField("logistics_fee")
    private BigDecimal logisticsFee;
    /**
     * 客户备注
     */
    private String remark;
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
     * 数据状态，0数据正常，1数据删除
     */
    @TableField("delete_flag")
    private Integer deleteFlag;
    /**
     * 1、待开箱，2、待提货,3、待发货，4、已发货
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 商品项目数量，不是商品
     */
    @TableField("product_count")
    private Integer productCount;
    /**
     * 订单物流编号
     */
    @TableField("orderlogistics_id")
    private Integer orderlogisticsId;
    /**
     * 订单支付渠道
     */
    @TableField("pay_channel")
    private String payChannel;
    /**
     * 第三方支付流水号
     */
    @TableField("out_trade_no")
    private String outTradeNo;
    /**
     * 用户表自动编号
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 开箱时间
     */
    @TableField("unpacking_time")
    private Date unpackingTime;
    /**
     * 支付类型
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 订单状态 0：已初始化待支付 1：已支付
     */
    @TableField("pay_status")
    private Integer payStatus;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
