package com.wenhui.project.web.vo;/*
 @author 天赋吉运-bms
 @DESCRIPTION 盲盒列表Vo
 @create 2023/3/6
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminBoxListVo {

    private Long boxId;
    /**
     * 盲盒标题
     */
    @ApiModelProperty("盲盒标题")
    private String title;
    /**
     * 盲盒价格
     */
    @ApiModelProperty("盲盒价格")
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
    private Integer sort;
    /**
     * 盲盒封面
     */
    @ApiModelProperty("盲盒封面")
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
    private String content;

    /**
     * 是否推荐首页（0不推荐，1推荐）
     */
    @ApiModelProperty("是否推荐首页（0不推荐，1推荐）")
    private Boolean recommend;

    /**
     * 购买人数
     */
    @ApiModelProperty("购买人数")
    private Integer buyNum;

    /**
     * 高级商品概率
     */
    @ApiModelProperty("高级商品概率")
    private Double hignProbability;

    /**
     * 稀有商品概率
     */
    @ApiModelProperty("稀有商品概率")
    private Double rarityProbability;

    /**
     * 史诗商品概率
     */
    @ApiModelProperty("史诗商品概率")
    private Double epicProbability;

    /**
     * 传说商品概率
     */
    @ApiModelProperty("传说商品概率")
    private Double legendProbability;

    @ApiModelProperty("商品概率")
    private List<BlindBoxProbability> box_probability;

    @Data
    public class BlindBoxProbability{

        public BlindBoxProbability(String box_name, Integer box_type, String probability) {
            this.box_name = box_name;
            this.box_type = box_type;
            this.probability = probability;
        }

        /**
         * 盒子名称
         */
        @ApiModelProperty("盒子名称")
        private String box_name;

        /**
         * 概率类型
         */
        @ApiModelProperty("概率类型")
        private Integer box_type;

        /**
         * 商品概率
         */
        @ApiModelProperty("商品概率")
        private String probability;
    }
}
