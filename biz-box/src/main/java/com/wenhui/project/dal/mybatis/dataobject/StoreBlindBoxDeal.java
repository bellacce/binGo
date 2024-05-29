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
 * 盲盒配置表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_blind_box_deal")
public class StoreBlindBoxDeal extends Model<StoreBlindBoxDeal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 简称
     */
    private String title;
    /**
     * 状态[1、正常；2、关闭]
     */
    private Integer status;
    /**
     * 颜色
     */
    private String color;
    /**
     * 真实中奖概率
     */
    @TableField("reality_rate")
    private BigDecimal realityRate;
    /**
     * 虚假中奖概率
     */
    @TableField("virtual_rate")
    private BigDecimal virtualRate;
    /**
     * 规则描述
     */
    private String remark;
    /**
     * 创建时间
     */
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
