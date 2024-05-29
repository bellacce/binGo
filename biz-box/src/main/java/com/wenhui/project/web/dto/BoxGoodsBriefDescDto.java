package com.wenhui.project.web.dto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BoxGoodsBriefDescDto {

    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 商品封面
     */
    @ApiModelProperty(value = "商品封面")
    private String thumb;
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal pice;

    public JSONArray getThumb() {
        JSONArray jsonArray = JSON.parseArray(thumb);
        return jsonArray;
    }
}
