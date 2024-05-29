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
 * 地区快递模板
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_freight_rule")
public class StoreFreightRule extends Model<StoreFreightRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 计费方式：1、按件数计费 2、按重量计费
     */
    @TableField("freight_type")
    private Integer freightType;
    /**
     * 关联id
     */
    @TableField("freight_id")
    private Integer freightId;
    /**
     * 计算数量(件、重量)
     */
    private Integer figure;
    /**
     * 运费金额
     */
    private BigDecimal amount;
    /**
     * 增加计算数量(件、重量)
     */
    @TableField("increase_figure")
    private Integer increaseFigure;
    /**
     * 增加运费金额
     */
    @TableField("increase_amount")
    private BigDecimal increaseAmount;
    /**
     * 条件（满多少包邮）
     */
    @TableField("cond_figure")
    private Integer condFigure;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
