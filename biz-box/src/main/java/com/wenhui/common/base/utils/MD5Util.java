package com.wenhui.common.base.utils;


import com.wenhui.core.core.biz.BizCoreException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * Encodes a string 2 MD5
     *
     * @param str String to encode
     * @return Encoded String
     * @throws NoSuchAlgorithmException
     */
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString().toUpperCase();
    }

    /**
     * 给定一个字符串 给出这个字符串的md5值
     *
     * @param pwd 密码
     * @return 返回 MD5值
     */
    public static String md5(String pwd) {
        return md5(pwd, "UTF-8");
    }

    /**
     * 给定一个字符串给出这个字符串的md5 字节值
     *
     * @param pwd 密码
     * @return 返md5字节数组
     */
    public static byte[] md5Byte(String pwd) {
        return md5Byte(pwd, "UTF-8");
    }

    /**
     * 给定一个字符串 给出这个字符串的md5值
     *
     * @param pwd         密码
     * @param charsetName 字符集名称
     * @return 返回 MD5值
     */
    public static String md5(String pwd, String charsetName) {
        return ByteUtil.bytes2String(md5Byte(pwd, charsetName));
    }

    /**
     * 给定一个字符串，给出这个字符串的MD5字节数组
     *
     * @param pwd         密码串
     * @param charsetName 字节码
     * @return 给出这个字符串的md5字节数组
     */
    public static byte[] md5Byte(String pwd, String charsetName) {
        byte[] md5Byte = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            md5Byte = messageDigest.digest(pwd.getBytes(Charset.forName(charsetName)));
        } catch (NoSuchAlgorithmException e) {
            throw new BizCoreException("MD5 operate exception");
        }
        return md5Byte;
    }

    /**
     * 获得MD5值
     *
     * @param pwd   密码
     * @param times MD5 加密的次数
     * @return MD5值
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String pwd, int times) {
        if (times < 1) {
            throw new BizCoreException("MD5 operate exception");
        }
        for (int i = 0; i < times; i++) {
            pwd = md5(pwd);
        }
        return pwd;
    }

    /**
     * 宽字符的MD5 相当于把MD5 的值再MD5 一次
     *
     * @param pwd 密码值
     * @return 返回加密后的值
     */
    public static String md5W(String pwd) {
        return md5(pwd, 2);
    }


    /**
     * 将源字符串使用MD5加密为字节数组
     *
     * @param source
     * @return
     */
    public static byte[] encode2bytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将源字符串使用MD5加密为32位16进制数
     *
     * @param source
     * @return
     */
    public static String encode2hex(String source) {
        byte[] data = encode2bytes(source);

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * 验证字符串是否匹配
     *
     * @param unknown 待验证的字符串
     * @param okHex   使用MD5加密过的16进制字符串
     * @return 匹配返回true，不匹配返回false
     */
    public static boolean validate(String unknown, String okHex) {
        return okHex.equals(encode2hex(unknown));
    }

    public static void main(String[] args) {
        String str = MD5Util.encode2hex("今天99");
        System.out.println("加密后为：" + str);
        System.out.println("是否匹配:" + MD5Util.validate("今天99", str));
    }
}
