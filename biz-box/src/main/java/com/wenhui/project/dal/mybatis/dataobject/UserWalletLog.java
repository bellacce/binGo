package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户钱包流水记录表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_wallet_log")
public class UserWalletLog extends Model<UserWalletLog> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户uid
     */
    private Integer uid;
    /**
     * 流水号
     */
    private String number;
    /**
     * 业务类型，1：充值，2：提现  3：结算
     */
    @TableField("target_type")
    private Integer targetType;
    /**
     * 变动的金额
     */
    @TableField("balance_fee")
    private BigDecimal balanceFee;
    /**
     * 经办人
     */
    @TableField("result_uid")
    private Integer resultUid;
    /**
     * 处理结果
     */
    private String remark;
    /**
     * 状态 0、待处理 1、处理完成 2、驳回
     */
    private Integer status;
    /**
     * 操作sql
     */
    @TableField("operation_sql")
    private String operationSql;
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
