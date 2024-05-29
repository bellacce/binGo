package com.wenhui.common.base.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: wh_shopbox
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-20 23:39
 **/
@Getter
public enum OpenBoxEnum {
    HIGN_PROBABILITY(1, "高级商品"),
    RARITY_PROBABILITY(2, "稀有商品"),
    EPIC_PROBABILITY(3, "史诗商品"),
    LEGEND_PROBABILITY(4, "传说商品");
    private Integer type;
    private String name;

    public static OpenBoxEnum getRecord(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (OpenBoxEnum record : OpenBoxEnum.values()) {
            if (record.getName().equals(name)) {
                return record;
            }
        }
        return null;
    }

    OpenBoxEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
