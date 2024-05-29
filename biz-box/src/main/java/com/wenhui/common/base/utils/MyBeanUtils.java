package com.wenhui.common.base.utils;/*
 @author 天赋吉运-bms
 @DESCRIPTION 对象处理工具
 @create 2023/3/4
*/

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class MyBeanUtils {
    /**
     * 配合beanutils设置对源对象中空的元素不进行复制，避免了目标对象某属性原本不为空，赋值后为空
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
