package com.wenhui.project.biz.config;/*
 @author 天赋吉运-bms
 @DESCRIPTION 利润分成
 @create 2023/3/18
*/

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebatePart {
    private BigDecimal userRebate;
    private BigDecimal userRecommendRebate;
    private BigDecimal terraceRebate;

    public RebatePart(BigDecimal userRebate, BigDecimal userRecommendRebate, BigDecimal terraceRebate) {
        this.userRebate = userRebate;
        this.userRecommendRebate = userRecommendRebate;
        this.terraceRebate = terraceRebate;
    }
}
