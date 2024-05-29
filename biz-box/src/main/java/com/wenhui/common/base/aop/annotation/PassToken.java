package com.wenhui.common.base.aop.annotation;

import java.lang.annotation.*;

/**
 * @program: wh_shopbox
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-12 11:10
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassToken {
    boolean required() default true;
}