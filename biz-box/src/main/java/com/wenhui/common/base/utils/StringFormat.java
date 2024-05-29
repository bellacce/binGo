package com.wenhui.common.base.utils;

/**
 * @Description :  [一句话描述该类的功能]
 * @Project : qmcy_flight
 * @ClassName：StringFormat
 * @Package Name :net.auio.qmcy_flight.util
 * @Author : 1643091610@qq.com
 * @Blog ：https://www.cnblogs.com/xiaohaojs/
 * @Date :2019 年 09月 20 日 16:30
 * @ModifcationHistory : ------Who----------When------------What----------
 */
public class StringFormat {


    /**
     * 删除方法一
     *
     * @param str
     * @param delChar
     * @return
     */
    public static String deleteString0(String str, char delChar) {
        String delStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != delChar) {
                delStr += str.charAt(i);
            }
        }
        return delStr;
    }

    /**
     * 删除方法四
     *
     * @param str
     * @param delChar
     * @return
     */
    public static String deleteString4(String str, char delChar) {
        String delStr = "";
        int iIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delChar) {
                if (i > 0) {
                    delStr += str.substring(iIndex, i);
                }
                iIndex = i + 1;
            }
        }
        if (iIndex <= str.length()) {
            delStr += str.substring(iIndex, str.length());
        }
        return delStr;
    }
}
