package com.wenhui.project.biz.enums;

public enum BizConfigKeyEnum {

    TEST("TEST", "测试"),

    KING("KING", "国王规则"),
    SMS_LOGIN_CODE("shopbox:sms:verifyCode:", "手机号的验证码"),
    SMS_LOGIN_COUNT("shopbox:sms:count:", "短信发送验证码次数"),
    ;

    private String code;

    private String description;

    BizConfigKeyEnum(String code, String description) {
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
