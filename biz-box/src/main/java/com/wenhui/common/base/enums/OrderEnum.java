package com.wenhui.common.base.enums;

import lombok.Getter;

@Getter
public enum OrderEnum {
    CACEL(0, "取消"),
    NEW_ORDER(1, "待支付"),
    PAY_ORDER(2, "已支付"),
    FINISHED(3, "已完成");
    private Integer code;
    private String message;

    OrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
