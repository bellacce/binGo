package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 商品添加Dto
 @create 2023/3/10
*/

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodUpdateDto {

    @ApiModelProperty("商品id")
    private Integer goodsId;
    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
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
    @NotNull(message = "商品价格不能为空")
    private BigDecimal goodsPrice;
    /**
     * 商品划线价
     */
    @ApiModelProperty("商品划线价")
    @NotNull(message = "商品原价不能为空")
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
    @NotEmpty(message = "商品封面不能为空")
    private String goodsThumb;
    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
//    @NotEmpty(message = "商品内容不能为空")
    private String content;
    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
    private String mdContent;
    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    @Size(min = 1, message = "商品封面不能为空")
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
    @NotNull(message = "商品类型不能为空")
    private Integer goodsType;
    /**
     * 商品视频封面
     */
    @ApiModelProperty("商品视频封面")
    private String goodsVideo;
    /**
     * 是否盲盒商品（0，不是，1是）
     */
    @ApiModelProperty("是否盲盒商品（0，不是，1是）")
    private Integer isBoxGood;
}
