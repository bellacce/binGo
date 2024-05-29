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
 * 商品
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_goods")
public class StoreGoods extends Model<StoreGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    private String title;
    /**
     * 运费模版id
     */
    @TableField("freight_id")
    private Integer freightId;
    /**
     * 商品价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;
    /**
     * 商品划线价
     */
    @TableField("line_price")
    private BigDecimal linePrice;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态[0、待上架; 1、已上架; 2、下架]
     */
    private Integer status;
    /**
     * 商品库存
     */
    @TableField("stock_num")
    private Integer stockNum;
    /**
     * 限购数量,0 则不限购
     */
    @TableField("limit_num")
    private Integer limitNum;
    /**
     * 商品销量
     */
    @TableField("goods_sales")
    private Integer goodsSales;
    /**
     * 赠送的成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;
    /**
     * 赠送的积分
     */
    @TableField("gift_point")
    private Integer giftPoint;
    /**
     * 限制使用的积分数
     */
    @TableField("use_point_limit")
    private Integer usePointLimit;
    /**
     * 库存预警值
     */
    @TableField("low_stock")
    private Integer lowStock;
    /**
     * 服务：1->无忧退货；2->快速退款；3->免费包邮
     */
    @TableField("service_ids")
    private String serviceIds;
    /**
     * 促销开始时间
     */
    @TableField("promotion_start_time")
    private Date promotionStartTime;
    /**
     * 促销结束时间
     */
    @TableField("promotion_end_time")
    private Date promotionEndTime;
    /**
     * 活动限购数量
     */
    @TableField("promotion_per_limit")
    private Integer promotionPerLimit;
    /**
     * 商品封面
     */
    @TableField("goods_thumb")
    private String goodsThumb;
    /**
     * 商品内容
     */
    private String content;
    /**
     * 商品内容
     */
    @TableField("md_content")
    private String mdContent;
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
     * 商品封面
     */
    @TableField("goods_cover")
    private String goodsCover;
    /**
     * 商品编号
     */
    @TableField("goods_no")
    private String goodsNo;
    /**
     * 商品类型
     */
    @TableField("goods_type")
    private Integer goodsType;
    /**
     * 商品视频封面
     */
    @TableField("goods_video")
    private String goodsVideo;

    /**
     * 是否推荐首页（0，不推荐，1推荐）
     */
    @TableField("recommend")
    private Integer recommend;
    /**
     * 是否盲盒商品
     */
    @TableField("is_box_good")
    private Integer isBoxGood;



    @Override
    protected Serializable pkVal() {
        return this.goodsId;
    }

}
