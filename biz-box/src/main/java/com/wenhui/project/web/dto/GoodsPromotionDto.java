package com.wenhui.project.web.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 商品活动专区列表
 @create 2023/2/10
*/
@Data
@Accessors(chain = true)
public class GoodsPromotionDto {

    private Integer goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品标题
     */
    @ApiModelProperty("商品标题")
    private String title;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /**
     * 商品价格
     */

    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
    private String content;

    @ApiModelProperty("商品内容")
    private String mdContent;

    /**
     * 商品划线价
     */
    @ApiModelProperty("商品划线价")
    private BigDecimal linePrice;
    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    private String goodsThumb;

    public JSONArray getGoodsThumb() {
        JSONArray jsonArray = JSON.parseArray(goodsThumb);
        return jsonArray;
    }
}
