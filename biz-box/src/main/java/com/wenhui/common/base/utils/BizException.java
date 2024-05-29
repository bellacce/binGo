package com.wenhui.common.base.utils;

public class BizException extends RuntimeException {


    private String code;
    private String msg;


    public BizException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public BizException(String msg) {
        this.msg = msg;
    }
}