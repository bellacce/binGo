package com.wenhui.project.web.dto;

import com.wenhui.common.base.utils.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminOrderListDto extends PageDto {

    private String start_time;
    private String end_time;
    private String user_id;

    private Integer orderStatus;

}
