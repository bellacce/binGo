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
 * 客户支付记录&代理提现记录
 * </p>
 *
 * @author XinHe
 * @since 2023-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("core_pay_record")
public class CorePayRecord extends Model<CorePayRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 代理收入-0，代理提现-1，代理冻结-2
     */
    private Integer type;
    /**
     * 关系人id
     */
    @TableField("relate_id")
    private Integer relateId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
