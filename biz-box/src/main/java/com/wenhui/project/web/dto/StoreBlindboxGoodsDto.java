package com.wenhui.project.web.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class StoreBlindboxGoodsDto {

    /**
     * 数据主键
     */
    @ApiModelProperty(value = "数据主键")
    private Integer id;
    /**
     * 归属盲盒id
     */
    @ApiModelProperty(value = "归属盲盒id")
    private Integer boxId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 商品封面
     */
    @ApiModelProperty(value = "商品封面")
    private String thumb;
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal pice;

    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    @ApiModelProperty(value = "商品标签：1高级 2稀有3史诗4传说")
    private Integer tag;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    @TableField("tag_text")
    private String tagText;

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    @TableField("goods_no")
    private String goodsNo;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;

    public JSONArray getThumb() {
        JSONArray jsonArray = JSON.parseArray(thumb);
        return jsonArray;
    }
}
