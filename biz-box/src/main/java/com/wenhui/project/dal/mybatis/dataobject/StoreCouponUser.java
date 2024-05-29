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
 * 用户优惠券表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_coupon_user")
public class StoreCouponUser extends Model<StoreCouponUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 用户id
     */
    private Integer uid;
    @TableField("coupon_id")
    private Long couponId;
    /**
     * 优惠类型
     */
    @TableField("coupon_type")
    private Integer couponType;
    /**
     * 优惠券编码
     */
    @TableField("code_no")
    private String codeNo;
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
     * 状态：-1已作废 0待使用 1已使用 2已过期
     */
    private Integer status;
    /**
     * 使用时间
     */
    @TableField("use_time")
    private Date useTime;
    /**
     * 优惠券描述
     */
    private String remark;
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
