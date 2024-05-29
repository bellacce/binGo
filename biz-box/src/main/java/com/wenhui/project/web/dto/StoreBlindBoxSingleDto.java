package com.wenhui.project.web.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单个商品盲盒
 */
@Data
@Accessors(chain = true)
public class StoreBlindBoxSingleDto {
    private Integer id;

    @ApiModelProperty(value = "盲盒标题")
    private String title;

    @ApiModelProperty(value = "盲盒价格")
    private BigDecimal price;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "盲盒封面")
    private String thumb;

    @ApiModelProperty(value = "盲盒规则")
    private String content;

    @ApiModelProperty(value = "盲盒规则")
    private String mdContent;

    @ApiModelProperty(value = "盒内商品列表")
    List<StoreBlindboxGoodsDto> blindboxGoods;

    public JSONArray getThumb() {
        JSONArray jsonArray = JSON.parseArray(thumb);
        return jsonArray;
    }
}
