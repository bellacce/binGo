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
 * 订单利润分成
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders_rebate")
public class OrdersRebate extends Model<OrdersRebate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 订单金额
     */
    @TableField("order_price")
    private BigDecimal orderPrice;
    /**
     * 用户收益
     */
    @TableField("user_rebate")
    private BigDecimal userRebate;
    /**
     * 推荐用户收益
     */
    @TableField("user_recommend_rebate")
    private BigDecimal userRecommendRebate;
    /**
     * 平台收益
     */
    @TableField("terrace_rebate")
    private BigDecimal terraceRebate;
    /**
     * 数据状态  0：正常
     */
    private Long state;
    /**
     * 数据修改说明
     */
    private String commit;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public OrdersRebate(Integer userId, Integer orderId, BigDecimal orderPrice, BigDecimal userRebate, BigDecimal userRecommendRebate, BigDecimal terraceRebate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.userRebate = userRebate;
        this.userRecommendRebate = userRecommendRebate;
        this.terraceRebate = terraceRebate;
    }
}
