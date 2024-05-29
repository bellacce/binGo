package com.wenhui.project.web.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.wenhui.project.dal.mybatis.dataobject.StoreBoxCategory;
import lombok.Data;

import java.util.Date;

/**
 * 盲盒分类管理
 */
@Data
public class BoxCategorDto {

    /**
     * 分类ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父级分类ID
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 排序
     */
    private Integer sort;


}
