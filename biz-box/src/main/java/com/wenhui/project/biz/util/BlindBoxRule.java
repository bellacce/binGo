package com.wenhui.project.biz.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 盲盒规则概率 实体
 *
 * @author JustJavaIt
 * @date 2022/01/29 14:18
 */
@Data
public class BlindBoxRule {

    private Long id;

    /**
     * 盲盒ID
     */
    private Long boxId;

    /**
     * 盲盒商品类型(1至尊,2超稀有,3稀有,4普通)
     */
    private Integer label;

    /**
     * 概率
     */
    private Integer realRate;

    public BlindBoxRule(Long id, Long boxId, Integer label, Integer realRate) {
        this.id = id;
        this.boxId = boxId;
        this.label = label;
        this.realRate = realRate;
    }

    /**
     * 根据用户抽的盲盒id为19,查询出对应商品类型的概率值。
     * 补充说明：每个盲盒id都有4种商品类型(1至尊,2超稀有,3稀有,4普通)，有不同的概率。每种类型下有不同的商品。
     *
     * @return
     */
    public static List<BlindBoxRule> queryByBoxId() {
        List<BlindBoxRule> boxRuleList = new ArrayList<>();
        BlindBoxRule rule1 = new BlindBoxRule(1L, 19L, 1, 200);
        BlindBoxRule rule2 = new BlindBoxRule(1L, 19L, 2, 600);
        BlindBoxRule rule3 = new BlindBoxRule(1L, 19L, 3, 3200);
        BlindBoxRule rule4 = new BlindBoxRule(1L, 19L, 4, 96000);
        boxRuleList.add(rule1);
        boxRuleList.add(rule2);
        boxRuleList.add(rule3);
        boxRuleList.add(rule4);

        return boxRuleList;
    }
}