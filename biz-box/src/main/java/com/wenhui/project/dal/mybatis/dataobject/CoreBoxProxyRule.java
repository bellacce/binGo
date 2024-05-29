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
 * 抽奖盒子：内测（达人）规则表
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("core_box_proxy_rule")
public class CoreBoxProxyRule extends Model<CoreBoxProxyRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 代理编号
     */
    @TableField("proxy_id")
    private Integer proxyId;
    /**
     * 一级商品概率
     */
    @TableField("one_probability")
    private Double oneProbability;
    /**
     * 二级商品概率
     */
    @TableField("two_probability")
    private Double twoProbability;
    /**
     * 三级商品概率
     */
    @TableField("three_probability")
    private Double threeProbability;
    /**
     * 四级商品概率
     */
    @TableField("four_probability")
    private Double fourProbability;
    /**
     * 五级商品概率
     */
    @TableField("five_probability")
    private Double fiveProbability;
    /**
     * 备用字段表
     */
    @TableField("ext_info")
    private String extInfo;
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
