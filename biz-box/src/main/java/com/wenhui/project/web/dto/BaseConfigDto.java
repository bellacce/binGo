package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 基础配置Dto
 @create 2023/3/19
*/

import lombok.Data;

@Data
public class BaseConfigDto {

    private Integer id;
    /**
     * 类型：1、用户协议 2、视频
     */
    private String type;
    private String typeString;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态：0 、启用, 1、关闭
     */
    private Integer status;
}
