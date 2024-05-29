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
 * 购物车
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_cart")
public class StoreCart extends Model<StoreCart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 商品id
     */
    @TableField("goods_id")
    private Integer goodsId;
    /**
     * 商品sku记录索引 (由规格id组成)
     */
    @TableField("spec_sku_id")
    private String specSkuId;
    /**
     * 商品编码
     */
    @TableField("goods_no")
    private String goodsNo;
    /**
     * 商品封面
     */
    @TableField("goods_thumb")
    private String goodsThumb;
    /**
     * 商品价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;
    /**
     * 商品划线价
     */
    @TableField("line_price")
    private BigDecimal linePrice;
    /**
     * 购买数量
     */
    private Integer number;
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
