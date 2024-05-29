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
 * 盲盒商品表
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_blindbox_goods")
public class StoreBlindboxGoods extends Model<StoreBlindboxGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 归属盲盒id
     */
    @TableField("box_id")
    private Integer boxId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品封面
     */
    private String thumb;
    /**
     * 商品价格
     */
    private BigDecimal pice;
    /**
     * 运费
     */
    private BigDecimal carriage;
    /**
     * 添加时间
     */
    @TableField("add_time")
    private Date addTime;
    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    private Integer tag;
    /**
     * 商品内容
     */
    private String content;
    /**
     * 商品内容
     */
    @TableField("md_content")
    private String mdContent;
    /**
     * 标签名称
     */
    @TableField("tag_text")
    private String tagText;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 商品状态1上架2下架
     */
    private Integer status;
    /**
     * 删除状态0正常1删除
     */
    @TableField("delete_flag")
    private Integer deleteFlag;
    /**
     * 商品编号
     */
    @TableField("goods_no")
    private String goodsNo;
    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 占得比重份数
     */
    private Integer probability;

    /**
     * 商品类型
     */
    @TableField("category_id")
    private Integer categoryId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
