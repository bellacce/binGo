package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 
 @create 2023/3/7
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminBoxGoodsAddDto {
    /**
     * 数据主键
     */
    private Integer id;
    /**
     * 归属盲盒id
     */
    @ApiModelProperty("归属盲盒id")
    @NotNull(message = "盲盒类型不能为空")
    private Integer boxId;
    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    @ApiModelProperty("商品标签")
    @NotNull(message = "稀有度不能为空")
    private Integer tag;
    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String tagText;
    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    @NotEmpty(message = "商品未选择")
    private String goodsNo;
    /**
     * 占得比重份数
     */
    @ApiModelProperty("占得比重份数")
//    @NotNull(message = "占得比重份数不能为空")
    private Integer probability;

    /**
     * 商品类型
     */
    @ApiModelProperty("商品类型")
    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;
}
