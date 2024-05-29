package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 
 @create 2023/3/7
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminCategoryVo {
    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    private Integer id;
    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    public AdminCategoryVo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
