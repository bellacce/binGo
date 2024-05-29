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
 * 优惠券模板表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_coupon")
public class StoreCoupon extends Model<StoreCoupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 优惠券类型：1直减现金券2满减现金券
     */
    @TableField("coupon_type")
    private String couponType;
    /**
     * 优惠金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;
    /**
     * 满减金额
     */
    @TableField("full_amount")
    private BigDecimal fullAmount;
    /**
     * 发放开始时间
     */
    @TableField("grant_start_time")
    private Date grantStartTime;
    /**
     * 发放结束时间
     */
    @TableField("grant_end_time")
    private Date grantEndTime;
    /**
     * 关联id
     */
    @TableField("ref_ids")
    private String refIds;
    /**
     * 生效时间
     */
    @TableField("effective_start_time")
    private Date effectiveStartTime;
    /**
     * 失效时间
     */
    @TableField("effective_end_time")
    private Date effectiveEndTime;
    /**
     * 状态:1、已提交;2、启用;3、发放中;4、暂停;5、结束;
     */
    private Integer status;
    /**
     * 发放数量
     */
    private Integer quantity;
    /**
     * 优惠券描述
     */
    private String remark;
    /**
     * 每人可重复领取数量
     */
    @TableField("repeat_quantity")
    private String repeatQuantity;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
