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
 * 快递模板
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_freight")
public class StoreFreight extends Model<StoreFreight> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 发货地
     */
    @TableField("ship_place")
    private String shipPlace;
    /**
     * 状态[1、正常；2、关闭]
     */
    private Integer status;
    /**
     * 包邮地区
     */
    @TableField("postage_free")
    private String postageFree;
    /**
     * 配送区域
     */
    @TableField("delivery_area")
    private String deliveryArea;
    /**
     * 不配送区域
     */
    @TableField("not_delivery")
    private String notDelivery;
    /**
     * 计费方式：1、按件数计费 2、按重量计费
     */
    @TableField("freight_type")
    private Integer freightType;
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
