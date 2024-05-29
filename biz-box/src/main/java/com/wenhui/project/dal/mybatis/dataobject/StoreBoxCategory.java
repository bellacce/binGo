package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 盲盒分类表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_box_category")
public class StoreBoxCategory extends Model<StoreBoxCategory> {

    private static final long serialVersionUID = 1L;

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
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 数据状态，0：删除，1：正常
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
