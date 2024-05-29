package com.wenhui.common.base.enums;

import lombok.Getter;

@Getter
public enum SubOrderEnum {
    CACEL(0, "取消"),
    PAY_NO_ORDER(1, "未支付"),
    NO_FINISHED(2, "未使用"),
    FINISHED(3, "已使用");
    private Integer code;
    private String message;

    SubOrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
