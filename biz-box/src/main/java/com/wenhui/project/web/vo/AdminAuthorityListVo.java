package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 获取指定用户的权限
 @create 2023/3/4
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdminAuthorityListVo {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;
    /**
     * 权限编码
     */
    @ApiModelProperty("权限编码")
    private String type;
    /**
     * 权限名字
     */
    @ApiModelProperty("权限名字")
    private String power_name;
    /**
     * 是否拥有
     */
    @ApiModelProperty("是否拥有")
    private Boolean has_power;
    /**
     * 角色排序
     */
    private Integer sort;
    /**
     * 组件名
     */
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 子列表
     */
    @ApiModelProperty("子列表")
    private List<AdminAuthorityListVo> child;

}
