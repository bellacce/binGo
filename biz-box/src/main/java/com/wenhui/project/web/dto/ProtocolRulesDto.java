package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProtocolRulesDto {

    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 协议名称
     */
    @ApiModelProperty(value = "协议名称")
    private String name;
    /**
     * 协议内容
     */
    @ApiModelProperty(value = "协议内容")
    private String contract;
}
