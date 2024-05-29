package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付方式
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payment_method")
public class PaymentMethod extends Model<PaymentMethod> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 支付方式名称
     */
    @TableField("pay_name")
    private String payName;
    /**
     * 支付方式ID
     */
    @TableField("pay_id")
    private String payId;
    /**
     * 渠道id
     */
    @TableField("CHANNEL_ID")
    private String channelId;
    /**
     * 支付方式 0开启1关闭
     */
    @TableField("pay_state")
    private Integer payState;
    /**
     * 乐观锁
     */
    @TableField("REVISION")
    private Integer revision;
    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField("CREATED_TIME")
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField("UPDATED_BY")
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField("UPDATED_TIME")
    private Date updatedTime;
    /**
     * 支付方式的费率
     */
    @TableField("pay_rate")
    private BigDecimal payRate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
