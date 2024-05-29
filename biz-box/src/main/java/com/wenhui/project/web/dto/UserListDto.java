package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 用户列表
 @create 2023/3/27
*/

import com.wenhui.common.base.utils.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserListDto  extends PageDto {

    /**
     * 只看达人
     */
    @ApiModelProperty("只看达人")
    private Boolean is_daRen;
}
