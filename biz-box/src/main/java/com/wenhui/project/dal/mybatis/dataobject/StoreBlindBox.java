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
 * 商品盲盒
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_blind_box")
public class StoreBlindBox extends Model<StoreBlindBox> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 盲盒标题
     */
    private String title;
    /**
     * 盲盒价格
     */
    private BigDecimal price;
    /**
     * 状态：0 、下架, 1、上架
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 购买人数
     */
    @TableField("buy_num")
    private Integer buyNum;
    /**
     * 盒子内商品的图片（最多6张）
     */
    private String goodsThumb;

    /**
     * 盲盒封面
     */
    private String thumb;
    /**
     * 赠送的积分
     */
    @TableField("gift_point")
    private Integer giftPoint;
    /**
     * 赠送的成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;
    /**
     * 盲盒规则
     */
    private String content;
    /**
     * 盲盒规则
     */
    @TableField("md_content")
    private String mdContent;
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

    /**
     * 是否推荐首页（0不推荐，1推荐）
     */
    @TableField("recommend")
    private Integer recommend;

    /**
     * 高级商品概率
     */
    @TableField("hign_probability")
    private Double hignProbability;

    /**
     * 稀有商品概率
     */
    @TableField("rarity_probability")
    private Double rarityProbability;

    /**
     * 史诗商品概率
     */
    @TableField("epic_probability")
    private Double epicProbability;

    /**
     * 传说商品概率
     */
    @TableField("legend_probability")
    private Double legendProbability;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
