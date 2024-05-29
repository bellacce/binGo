package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 活动管理
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_activity")
public class StoreActivity extends Model<StoreActivity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 跳转类型： 0、商品Id 1、链接
     */
    @TableField("jump_type")
    private Integer jumpType;
    /**
     * 图片地址
     */
    private String thumb;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 小程序Id
     */
    @TableField("app_id")
    private String appId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 状态：0 、启用, 1、关闭
     */
    private Integer status;
    /**
     * 跳转地址
     */
    @TableField("jump_url")
    private String jumpUrl;
    private String remark;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
