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
 * 字典表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;
    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 删除状态
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 字典类型0为string,1为number
     */
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
