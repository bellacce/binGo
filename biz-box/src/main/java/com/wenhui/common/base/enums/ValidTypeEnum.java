package com.wenhui.common.base.enums;

/**
 * @author hz
 * @desc 记录是否有效的分布枚举
 **/
public enum ValidTypeEnum {

    VALID("VALID", 1),

    INVALID("INVALID", 0),
    ;

    private String code;

    private int value;

    public String getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }

    ValidTypeEnum(String code, int value) {
        this.code = code;
        this.value = value;
    }

}
