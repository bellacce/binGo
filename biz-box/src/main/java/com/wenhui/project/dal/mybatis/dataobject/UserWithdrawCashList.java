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
 * 提现记录表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_withdraw_cash_list")
public class UserWithdrawCashList extends Model<UserWithdrawCashList> {

    private static final long serialVersionUID = 1L;

    /**
     * auto id
     */
    private Integer id;
    /**
     * 申请用户uid
     */
    private String uid;
    /**
     * 提现（渠道）方式 1银行转账
     */
    @TableField("withdraw_way")
    private Integer withdrawWay;
    /**
     * 处理状态。 1发起申请（待审核理）前台显示处理中，2提现成功，3审核不通过
     */
    @TableField("withdraw_status")
    private Integer withdrawStatus;
    /**
     * 提现单号
     */
    private String number;
    /**
     * 收款账户
     */
    @TableField("receivable_account")
    private String receivableAccount;
    /**
     * 收款人姓名
     */
    private String name;
    /**
     * 开户行地址
     */
    private String address;
    /**
     * 提现金额
     */
    @TableField("withdraw_fee")
    private BigDecimal withdrawFee;
    /**
     * 审核不通过原因
     */
    private String content;
    /**
     * 审核人
     */
    @TableField("verify_user")
    private String verifyUser;
    /**
     * 操作人
     */
    @TableField("action_user")
    private String actionUser;
    /**
     * 审核时间
     */
    @TableField("action_at")
    private Date actionAt;
    /**
     * 发送通知时间
     */
    @TableField("sent_notice_at")
    private Date sentNoticeAt;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;
    /**
     * 是否删除：0未删除，1已删除
     */
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
