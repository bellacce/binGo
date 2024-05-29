package com.wenhui.common.base.enums;

import lombok.Getter;

/**
 * @program: wh_shopbox
 * @description: 支付类型
 * @author: Mr.Wang
 * @create: 2023-02-10 23:20
 **/
@Getter
public enum PayTyprEnum {
    DMF(4, "支付宝当面付"),
    QQPAY(3, "QQ钱包"),
    ALIPAY(2, "支付宝"),
    WXPAY(1, "微信支付");
    private Integer code;
    private String message;

    PayTyprEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
