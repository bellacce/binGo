package com.wenhui.common.base.enums;

/**
 * @author hz
 * @desc 系统日志模板枚举 主要用于规范日志输出
 * 1.暂时代替神策事件统计打点
 * 2.方便后续基于规律的日志分析添加业务监控
 **/
public enum LogTemplateEnum {

    ANCHOR_LOG_PATTERN("ANCHOR_LOG_PATTERN", "埋点日志 ------- 日志事件:[%1$s], 内容描述:[%2$s]"),

    REG_LOG_PATTERN("REG_LOG_PATTERN", "用户注册 ------- 手机号:[%1$s]"),

    VERIFY_LOGIN_LOG_PATTERN("VERIFY_LOGIN_LOG_PATTERN", "验证码登录 ------- 手机号:[%1$s], 验证码:[%2$s]"),
    ;

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    LogTemplateEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
