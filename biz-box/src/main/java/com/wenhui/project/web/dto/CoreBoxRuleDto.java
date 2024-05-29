package com.wenhui.project.web.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * xinhe
 */
@Data
public class CoreBoxRuleDto {

    /**
     * 一级商品概率
     */
    private BigDecimal oneProbability;
    /**
     * 二级商品概率
     */
    private BigDecimal twoProbability;
    /**
     * 三级商品概率
     */
    private BigDecimal threeProbability;

    /**
     * 一级商品概率
     */
    private String oneName;
    /**
     * 二级商品概率
     */
    private String twoName;
    /**
     * 三级商品概率
     */
    private String threeName;


}
