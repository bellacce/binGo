package com.wenhui.project.web.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WinningNotificationDto {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "noticName")
    private String noticName;
}
