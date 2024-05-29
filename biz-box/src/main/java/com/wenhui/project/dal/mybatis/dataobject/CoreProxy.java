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
 * 代理表
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("core_proxy")
public class CoreProxy extends Model<CoreProxy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String mobile;
    private String qq;
    @TableField("live_address")
    private String liveAddress;
    @TableField("invite_code")
    private String inviteCode;
    /**
     * 即是达人也是客户
     */
    @TableField("cus_id")
    private Integer cusId;
    /**
     * 推广提成比例
     */
    @TableField("extension_scale")
    private BigDecimal extensionScale;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 已提现金额
     */
    @TableField("extension_withdraw")
    private BigDecimal extensionWithdraw;
    /**
     * 冻结金额
     */
    @TableField("extension_freeze")
    private BigDecimal extensionFreeze;
    /**
     * 提现中
     */
    @TableField("extension_withdrawing")
    private BigDecimal extensionWithdrawing;
    /**
     * 账户余额
     */
    @TableField("extension_amount")
    private BigDecimal extensionAmount;
    /**
     * 推广状态 0-正常 1-冻结 2-已注销
     */
    @TableField("extension_status")
    private Integer extensionStatus;
    /**
     * 推广策略配置
     */
    @TableField("extension_rule")
    private String extensionRule;
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
     * 密码
     */
    private String password;
    /**
     * 微信二维码url
     */
    @TableField("weixin_url")
    private String weixinUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
