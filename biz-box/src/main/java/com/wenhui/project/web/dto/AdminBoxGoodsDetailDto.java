package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 
 @create 2023/3/7
*/

import com.wenhui.common.base.utils.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminBoxGoodsDetailDto extends PageDto {

    @ApiModelProperty("归属盲盒id")
    private Integer boxId;
    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    @ApiModelProperty("商品标签")
    private Integer tag;

    /**
     * 商品类型id
     */
    @ApiModelProperty("商品类型id")
    private Integer categoryId;
}
