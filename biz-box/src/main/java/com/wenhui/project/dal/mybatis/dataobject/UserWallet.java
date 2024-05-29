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
 * 用户钱包
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_wallet")
public class UserWallet extends Model<UserWallet> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 状态：0、正常 1、冻结
     */
    private Integer status;
    /**
     * 钱包总收入额
     */
    @TableField("wallet_income")
    private BigDecimal walletIncome;
    /**
     * 钱包总支出额
     */
    @TableField("wallet_outcome")
    private BigDecimal walletOutcome;
    /**
     * 钱包总可用余额
     */
    @TableField("balance_fee")
    private BigDecimal balanceFee;
    /**
     * 用于安全检查，检查不通过为异常。
     */
    @TableField("check_sign")
    private String checkSign;
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
