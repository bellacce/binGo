package com.wenhui.project.web.dto;/*
 @author 天赋吉运-bms
 @DESCRIPTION 新增/修改盲盒Dto
 @create 2023/3/6
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AdminStoreBlindBoxDto {

    private Long boxId;
    /**
     * 盲盒标题
     */
    @ApiModelProperty("盲盒标题")
    @NotEmpty(message = "盲盒名称不能为空")
    private String title;
    /**
     * 盲盒价格
     */
    @ApiModelProperty("盲盒价格")
    @NotNull(message = "盲盒价格不能为空")
    private BigDecimal price;
    /**
     * 状态：0 、下架, 1、上架
     */
    @ApiModelProperty("状态：0 、下架, 1、上架")
    private Boolean status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    /**
     * 盲盒封面
     */
    @ApiModelProperty("盲盒封面")
    @NotEmpty(message = "盲盒封面不能为空")
    private String thumb;
    /**
     * 赠送的积分
     */
    @ApiModelProperty("赠送的积分")
    private Integer giftPoint;
    /**
     * 赠送的成长值
     */
    @ApiModelProperty("赠送的成长值")
    private Integer giftGrowth;
    /**
     * 盲盒规则
     */
    @ApiModelProperty("盲盒规则")
    @NotEmpty(message = "盲盒规则不能为空")
    private String content;

    /**
     * 是否推荐首页（0不推荐，1推荐）
     */
    @ApiModelProperty("是否推荐首页（0不推荐，1推荐）")
    @NotNull(message = "是否推荐首页不能为空")
    private Boolean recommend;

    /**
     * 高级商品概率
     */
    @ApiModelProperty("高级商品概率")
    @NotNull(message = "高级商品概率不能为空")
    @Digits(integer=3,fraction = 4, message = "高级商品概率格式错误（只能输入有八位小数的正实数）")
    private Double hignProbability;

    /**
     * 稀有商品概率
     */
    @ApiModelProperty("稀有商品概率")
    @NotNull(message = "稀有商品概率不能为空")
    @Digits(integer=3,fraction = 4, message = "稀有商品概率格式错误（只能输入有八位小数的正实数）")
    private Double rarityProbability;

    /**
     * 史诗商品概率
     */
    @ApiModelProperty("史诗商品概率")
    @NotNull(message = "史诗商品概率不能为空")
    @Digits(integer=3,fraction = 4, message = "史诗商品概率格式错误（只能输入有八位小数的正实数）")
    private Double epicProbability;

    /**
     * 传说商品概率
     */
    @ApiModelProperty("传说商品概率")
    @NotNull(message = "传说商品概率不能为空")
    @Digits(integer=3,fraction = 4, message = "传说商品概率格式错误（只能输入有八位小数的正实数）")
    private Double legendProbability;

}
