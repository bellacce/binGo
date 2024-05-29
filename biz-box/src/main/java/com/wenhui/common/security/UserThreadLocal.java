package com.wenhui.common.security;

import com.wenhui.common.security.jwt.JwtUser;

/**
 * @program: wh_shopbox
 * @description: 用户本地线程类，存放请求的用户信息
 * @author: Mr.Wang
 * @create: 2023-02-19 15:07
 **/
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    //线程变量隔离
    private static final ThreadLocal<JwtUser> LOCAL = new ThreadLocal<>();

    public static void put(JwtUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static JwtUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
