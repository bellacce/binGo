package com.wenhui.integration.cos;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 腾讯cos连接对象
 @create 2023/2/22
*/
//@Data
@Component
public class CosUploadConstant implements InitializingBean {
    /**
     * URL地址
     */
    @Value("${tencent.cos.secretId}")
    private String secretId;
    /**
     * 商户ID
     */
    @Value("${tencent.cos.secretKey}")
    private String secretKey;
    /**
     * 异步通知地址
     */
    @Value("${tencent.cos.durationSeconds}")
    private Integer durationSeconds;
    /**
     * 跳转通知地址
     */
    @Value("${tencent.cos.bucket}")
    private String bucket;
    /**
     * 商品金额
     */
    @Value("${tencent.cos.region}")
    private String region;
    /**
     * cos根链接
     */
    @Value("${tencent.cos.baseurl}")
    private String baseurl;


    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static Integer DURATION_SECONDS;
    public static String BUCKET;
    public static String REGION;
    public static String BASEURL;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
        DURATION_SECONDS = durationSeconds;
        BUCKET = bucket;
        REGION = region;
        BASEURL = baseurl;
    }

    public static Map<String, String> sysPrint(){
        Map<String, String> map = new HashMap<>();
        map.put("name","CosUploadConstant");
        map.put("REGION",REGION);
        map.put("SECRET_ID",SECRET_ID);
        map.put("SECRET_KEY",SECRET_KEY);
        map.put("DURATION_SECONDS",DURATION_SECONDS+"");
        map.put("BUCKET",BUCKET);
        map.put("BASEURL",BASEURL);
        return map;
    }
}
