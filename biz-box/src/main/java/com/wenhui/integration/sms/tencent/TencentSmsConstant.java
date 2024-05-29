package com.wenhui.integration.sms.tencent;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯云短信验证码服务常量
 */
@Component
public class TencentSmsConstant implements InitializingBean {
    @Value("${tencent.sms.region}")
    private String region;

    @Value("${tencent.sms.secret_id}")
    private String secretId;

    @Value("${tencent.sms.secret_key}")
    private String secretKey;

    @Value("${tencent.sms.endpoint}")
    private String endpoint;

    @Value("${tencent.sms.sms_sdk_app_id}")
    private String smsSdkAppId;

    @Value("${tencent.sms.sign_name}")
    private String signName;

    @Value("${tencent.sms.template_id}")
    private String templateId;

    @Value("${tencent.sms.expired_time}")
    private String expiredTime;

    public static String REGION;
    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String ENDPOINT;
    public static String SMS_SDK_APP_ID;
    public static String SIGN_NAME;
    public static String TEMPLATE_ID;
    public static String EXPIRED_TIME;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION = region;
        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
        ENDPOINT = endpoint;
        SMS_SDK_APP_ID = smsSdkAppId;
        SIGN_NAME = signName;
        TEMPLATE_ID = templateId;
        EXPIRED_TIME = expiredTime;
    }

    public static Map<String, String> sysPrint(){
        Map<String, String> map = new HashMap<>();
        map.put("name","TencentSmsConstant");
        map.put("REGION",REGION);
        map.put("SECRET_ID",SECRET_ID);
        map.put("SECRET_KEY",SECRET_KEY);
        map.put("ENDPOINT",ENDPOINT);
        map.put("SMS_SDK_APP_ID",SMS_SDK_APP_ID);
        map.put("SIGN_NAME",SIGN_NAME);
        map.put("TEMPLATE_ID",TEMPLATE_ID);
        map.put("EXPIRED_TIME",EXPIRED_TIME);
        return map;
    }
}
