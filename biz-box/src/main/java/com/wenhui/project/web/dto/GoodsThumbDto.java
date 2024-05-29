package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GoodsThumbDto {

    /**
     * 商品名称
     */
    @ApiModelProperty("图片名称")
    private String name;

    /**
     * 商品名称
     */
    @ApiModelProperty("图片地址")
    private String goodsName;
}
