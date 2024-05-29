package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 权限菜单表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("authority")
@NoArgsConstructor
public class Authority extends Model<Authority> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 上级主键
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 权限名称
     */
    private String title;
    /**
     * 权限编码
     */
    private String key;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 组件路径
     */
    private String url;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
