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
 * 商品评论
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_goods_comment")
public class StoreGoodsComment extends Model<StoreGoodsComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户Uid
     */
    private Integer uid;
    /**
     * 商品Id
     */
    @TableField("goods_id")
    private Integer goodsId;
    /**
     * 商品订单ID
     */
    @TableField("order_goods_id")
    private Integer orderGoodsId;
    /**
     * 购买的数量
     */
    private Integer num;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 规格
     */
    private String sku;
    /**
     * 状态[0、审核中; 1、审核通过; 2、审核驳回]
     */
    private Integer status;
    /**
     * 商品封面
     */
    @TableField("goods_thumb")
    private String goodsThumb;
    /**
     * 评论的图片
     */
    private String picture;
    /**
     * 会员昵称
     */
    @TableField("member_name")
    private String memberName;
    /**
     * 评论用户头像
     */
    @TableField("member_icon")
    private String memberIcon;
    /**
     * 评价等级，1、表示非常差，2、表示差，3、表示一般，4、表示好，5、表示非常好
     */
    @TableField("comment_rank")
    private Integer commentRank;
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
