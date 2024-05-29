package com.wenhui.project.web.vo;


import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictItemListVo {

    @ApiModelProperty(value = "Id")
    private Integer id;

    @ApiModelProperty(value = "字典名字")
    private String itemText;

    @ApiModelProperty(value = "字典项值")
    private String itemValue;
    @ApiModelProperty(value = "描述")
    private String description;
}
