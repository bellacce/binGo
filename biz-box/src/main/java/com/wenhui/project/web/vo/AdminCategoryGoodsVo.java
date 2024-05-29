package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 
 @create 2023/3/7
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminCategoryGoodsVo {
    /**
     * 分类id
     */
    @ApiModelProperty("商品id")
    private Integer shopId;
    /**
     * 分类名称
     */
    @ApiModelProperty("商品名称")
    private String shopName;

    @ApiModelProperty("是否虚拟商品")
    private Boolean shopIscard;

    @ApiModelProperty("价格")
    private String shopPrice;

    public AdminCategoryGoodsVo(Integer shopId, String shopName, Boolean shopIscard, String shopPrice) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopIscard = shopIscard;
        this.shopPrice = shopPrice;
    }
}
