package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 
 @create 2023/3/7
*/

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminBoxGoodsDetailVo {

    /**
     * 数据主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 归属盲盒id
     */
    @ApiModelProperty("box_id")
    private Integer boxId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品封面
     */
    private List<ImageBaseVo> thumbs;
    /**
     * 商品价格
     */
    private BigDecimal pice;
    /**
     * 运费
     */
    private BigDecimal carriage;
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
    @ApiModelProperty("商品内容")
    private String mdContent;
    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String tagText;
    /**
     * 商品状态1上架2下架
     */
    private Integer status;
    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String goodsNo;
    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 占得比重份数
     */
    @ApiModelProperty("占得比重份数")
    private Integer probability;

    /**
     * 商品类型
     */
    @ApiModelProperty("商品类型")
    private Integer categoryId;
}
