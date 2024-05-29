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
 * 用户信息
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_user")
public class StoreUser extends Model<StoreUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;
    /**
     * 性别:0、保密 1、男 2、女
     */
    private Integer sex;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    @TableField("avatar_url")
    private String avatarUrl;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 账号状态[0、 正常;1、禁用]
     */
    private Integer status;
    /**
     * 手机号码
     */
    private String mobile;
    private String openid;
    /**
     * 唯一码
     */
    @TableField("invite_code")
    private String inviteCode;
    /**
     * 账户密码
     */
    private String password;
    @TableField("remember_token")
    private String rememberToken;
    /**
     * 注册IP
     */
    @TableField("register_ip")
    private String registerIp;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * vip等级
     */
    @TableField("vip_lev")
    private Integer vipLev;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 成长值
     */
    private Integer growth;
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
     * 推荐用户id 账号类型是1时 代表创建账号id
     */
    @TableField("recommend_id")
    private Integer recommendId;
    /**
     * 权限
     */
    @TableField("user_role")
    private String userRole;
    /**
     * 账号类型 0：普通用户 1：后台用户 2: h5手机用户
     */
    private Integer type;
    /**
     * 代理编号
     */
    @TableField("proxy_id")
    private Integer proxyId;
    /**
     * 金币数量
     */
    @TableField("gold_amount")
    private Integer goldAmount;
    /**
     * 抽奖次数
     */
    private Integer count;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
