package com.wenhui.project.web.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlindBoxRatioDto {

    /**
     * 高级商品概率
     */
    @ApiModelProperty(value = "高级商品概率")
    private Double hignProbability;

    /**
     * 稀有商品概率
     */
    @ApiModelProperty(value = "稀有商品概率")
    private Double rarityProbability;

    /**
     * 史诗商品概率
     */
    @ApiModelProperty(value = "史诗商品概率")
    private Double epicProbability;

    /**
     * 传说商品概率
     */
    @ApiModelProperty(value = "传说商品概率")
    private Double legendProbability;
}
