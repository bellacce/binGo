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
 * 商品盲盒规则表
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_blind_box_rule")
public class StoreBlindBoxRule extends Model<StoreBlindBoxRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 盲盒Id
     */
    @TableField("blind_box_id")
    private Long blindBoxId;
    /**
     * 关联商品id
     */
    @TableField("goods_id")
    private Long goodsId;
    /**
     * 简称
     */
    private String title;
    /**
     * 规则类型:1、隐藏款 2、稀有 3、史诗 4、传说
     */
    @TableField("rule_type")
    private Integer ruleType;
    /**
     * 商品价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;
    /**
     * 抽中人数
     */
    @TableField("draw_num")
    private Integer drawNum;
    /**
     * 奖励积分
     */
    @TableField("gift_point")
    private Integer giftPoint;
    /**
     * 奖励成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 商品主图无底色
     */
    @TableField("goods_thumb")
    private String goodsThumb;
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
