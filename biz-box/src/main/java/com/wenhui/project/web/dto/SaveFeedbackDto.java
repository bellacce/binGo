package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wh_shopbox
 * @description: 投诉与建议保存
 * @author: Mr.Wang
 * @create: 2023-02-12 16:13
 **/
@Data
public class SaveFeedbackDto {

    /**
     * 问题描述
     */
    @ApiModelProperty("问题描述")
    private String problemDescribe;
    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 截图
     */
//    @ApiModelProperty("截图")
//    private String images;
    /**
     * 视频
     */
//    @ApiModelProperty("视频")
//    private String videos;
    /**
     * 提交用户id
     */
    @ApiModelProperty("提交用户id")
//    @NotNull(message = "用户id不能为空")
    private Integer uid;
}
