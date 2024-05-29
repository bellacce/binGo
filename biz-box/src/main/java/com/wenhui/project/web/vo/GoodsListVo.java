package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 商品列表Vo
 @create 2023/3/10
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodsListVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    private Integer goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    private String title;
    /**
     * 运费模版id
     */
    @ApiModelProperty("运费模版id")
    private Integer freightId;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;
    /**
     * 商品划线价
     */
    @ApiModelProperty("商品划线价")
    private BigDecimal linePrice;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态[0、待上架; 1、已上架; 2、下架]
     */
    private Boolean status;
    /**
     * 商品库存
     */
    @ApiModelProperty("商品库存")
    private Integer stockNum;
    /**
     * 限购数量,0 则不限购
     */
    @ApiModelProperty("限购数量,0 则不限购")
    private Integer limitNum;
    /**
     * 商品销量
     */
    @ApiModelProperty("商品销量")
    private Integer goodsSales;
    /**
     * 赠送的成长值
     */
    @ApiModelProperty("赠送的成长值")
    private Integer giftGrowth;
    /**
     * 赠送的积分
     */
    @ApiModelProperty("赠送的积分")
    private Integer giftPoint;
    /**
     * 限制使用的积分数
     */
    @ApiModelProperty("限制使用的积分数")
    private Integer usePointLimit;
    /**
     * 库存预警值
     */
    @ApiModelProperty("库存预警值")
    private Integer lowStock;
    /**
     * 服务：1->无忧退货；2->快速退款；3->免费包邮
     */
    @ApiModelProperty("服务：1->无忧退货；2->快速退款；3->免费包邮")
    private String serviceIds;
    /**
     * 促销开始时间
     */
    @ApiModelProperty("促销开始时间")
    private Date promotionStartTime;
    /**
     * 促销结束时间
     */
    @ApiModelProperty("促销结束时间")
    private Date promotionEndTime;
    /**
     * 活动限购数量
     */
    @ApiModelProperty("活动限购数量")
    private Integer promotionPerLimit;
    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    private String goodsThumb;
    /**
     * 商品封面
     */
    private List<ImageBaseVo> thumbs;
    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
    private String content;
    /**
     * 商品内容
     */
    @ApiModelProperty("md_content")
    private String mdContent;
    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    private List<String> goodsCover;
    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String goodsNo;
    /**
     * 商品类型
     */
    @ApiModelProperty("商品类型")
    private Integer goodsType;
    @ApiModelProperty("商品类型")
    private String goodsTypeString;
    /**
     * 商品视频封面
     */
    @ApiModelProperty("商品视频封面")
    private String goodsVideo;
    /**
     * 是否盲盒商品
     */
    @ApiModelProperty("是否盲盒商品")
    private Integer isBoxGood;
    /**
     * 是否盲盒商品（0，不是，1是）
     */
    @ApiModelProperty("是否盲盒商品（0，不是，1是）")
    private String isBoxGoodString;
}
