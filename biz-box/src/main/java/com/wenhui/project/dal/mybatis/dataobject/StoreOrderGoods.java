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
 * 订单商品
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_order_goods")
public class StoreOrderGoods extends Model<StoreOrderGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 商品Id
     */
    @TableField("goods_id")
    private Integer goodsId;
    /**
     * 价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;
    /**
     * 原价格
     */
    @TableField("line_price")
    private BigDecimal linePrice;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 规格
     */
    private String sku;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 优惠券
     */
    @TableField("coupon_discount")
    private BigDecimal couponDiscount;
    /**
     * 抵扣积分
     */
    @TableField("deductt_point")
    private Integer deducttPoint;
    /**
     * 奖励积分
     */
    @TableField("gift_point")
    private Integer giftPoint;
    /**
     * 奖励成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;
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
    @TableField("goods_thumb")
    private String goodsThumb;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
