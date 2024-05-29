package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 商品列表
 @create 2023/3/10
*/

import com.wenhui.common.base.utils.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsListDto extends PageDto {

    @ApiModelProperty("商品分类")
    private Integer goodsType;
    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    @ApiModelProperty("商品名称")
    private String search;
}
