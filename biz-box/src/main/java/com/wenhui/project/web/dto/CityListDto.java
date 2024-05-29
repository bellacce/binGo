package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: wh_shopbox
 * @description: 城市listDto
 * @author: Mr.Wang
 * @create: 2023-02-12 16:53
 **/
@Data
public class CityListDto {

    @ApiModelProperty("城市id")
    private Integer id;

    @ApiModelProperty("查询code编码下的所有地区列表，查询省级列表可不传code  1:省 2:市 3:区")
    @NotNull(message = "请传入查询类型")
    private String type;
}
