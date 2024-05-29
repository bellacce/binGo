package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.enums.IdType;
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
 * 支付渠道 
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payment_channel")
public class PaymentChannel extends Model<PaymentChannel> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 渠道名称
     */
    @TableField("CHANNEL_NAME")
    private String channelName;
    /**
     * 渠道ID
     */
    @TableField("CHANNEL_ID")
    private String channelId;
    /**
     * 渠道ID
     */
    @TableField("channel_type")
    private String channelType;
    /**
     * 商户id
     */
    @TableField("MERCHANT_ID")
    private String merchantId;
    /**
     * 同步回调URL
     */
    @TableField("SYNC_URL")
    private String syncUrl;
    /**
     * 异步回调URL
     */
    @TableField("ASYN_URL")
    private String asynUrl;
    /**
     * 公钥
     */
    @TableField("PUBLIC_KEY")
    private String publicKey;
    /**
     * 私钥
     */
    @TableField("PRIVATE_KEY")
    private String privateKey;
    /**
     * 渠道状态 0开启1关闭
     */
    @TableField("CHANNEL_STATE")
    private Integer channelState;
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
     * 类全路径-设计模式使用
     */
    @TableField("CLASS_ADDRES")
    private String classAddres;

    /**
     * 网关URL
     */
    @TableField("CHANNEL_URL")
    private String channelUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
