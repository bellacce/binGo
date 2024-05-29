package com.wenhui.common.base.utils;/*
 @author 天赋吉运-bms
 @DESCRIPTION 分页Dto
 @create 2023/3/4
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage = 1;
    /**
     * 页面数量
     */
    @ApiModelProperty("页面数量")
    private Integer size = 10;

    @ApiModelProperty("id")
    private String id;
}
