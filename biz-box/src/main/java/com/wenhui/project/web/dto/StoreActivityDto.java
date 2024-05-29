package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * fh
 */
@Data
@Accessors(chain = true)
public class StoreActivityDto {

    private Integer id;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "'跳转类型： 0、商品Id 1、链接',")
    private String jumpType;

    @ApiModelProperty(value = "图片地址")
    private String imgFile;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "跳转地址")
    private String jumpUrl;

}
