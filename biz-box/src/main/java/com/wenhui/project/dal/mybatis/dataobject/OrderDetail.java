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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_detail")
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "order_detail_id", type = IdType.AUTO)
    private Integer orderDetailId;
    /**
     * 订单编号
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 商品编号
     */
    @TableField("goods_id")
    private Integer goodsId;

    private Integer type;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 商品价格
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    /**
     * 购买数量 
     */
    private Integer number;
    /**
     * 商品主图
     */
    @TableField("product_thumb")
    private String productThumb;
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
    /**
     * 数据状态0正常1删除2隐藏
     */
    @TableField("delete_flag")
    private Integer deleteFlag;


    @Override
    protected Serializable pkVal() {
        return this.orderDetailId;
    }

}
