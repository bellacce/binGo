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
 * 广告管理
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_advert")
public class StoreAdvert extends Model<StoreAdvert> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;
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
     * 跳转类型： 0、商品Id 1、链接
     */
    @TableField("jump_type")
    private Integer jumpType;
    /**
     * 图片地址
     */
    private String thumb;
    /**
     * 跳转地址
     */
    @TableField("jump_url")
    private String jumpUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 背景颜色
     */
    private String bgcolor;
    /**
     * 类型：1、无限期  2、 限制时间
     */
    private Integer type;
    /**
     * 类型： 0、商品分类 1、轮播图 2、栏目 3、其他
     */
    private Integer module;
    /**
     * 描述
     */
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
