package com.wenhui.project.web.dto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderGoodsListDto {

    /**
     * 自动编号
     */
    @ApiModelProperty(value = "自动编号")
    private Integer orderDetailId;
    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    private Integer goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;
    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer number;
    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图")
    private String productThumb;




    public JSONArray getProductThumb() {
        JSONArray jsonArray = JSON.parseArray(productThumb);
        return jsonArray;
    }
}
