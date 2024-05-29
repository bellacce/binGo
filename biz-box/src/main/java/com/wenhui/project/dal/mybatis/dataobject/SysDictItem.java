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
 * 字典项
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_item")
public class SysDictItem extends Model<SysDictItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典id
     */
    @TableField("dict_id")
    private Integer dictId;
    /**
     * 字典项文本
     */
    @TableField("item_text")
    private String itemText;
    /**
     * 字典项值
     */
    @TableField("item_value")
    private String itemValue;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;
    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;
    @TableField("del_flag")
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
