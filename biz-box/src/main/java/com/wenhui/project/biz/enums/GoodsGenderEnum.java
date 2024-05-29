package com.wenhui.project.biz.enums;

/**
 * 商品等级
 */
public enum GoodsGenderEnum {

    GENDER_ONE("1", "普通"),
    GENDER_TWO("2", "稀有"),
    GENDER_THREE("3", "至尊"),
    ;

    private String code;

    private String description;

    GoodsGenderEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
