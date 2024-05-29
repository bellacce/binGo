package com.wenhui.integration.pay.alipay;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 支付乐服务调用常量
 */
@Component
public class AlipayServiceCallConstant implements InitializingBean {
    /**
     * URL地址
     */
    @Value("${pay.alipay.url}")
    private String url;
    /**
     * 商户ID
     */
    @Value("${pay.alipay.uid}")
    private Integer uid;
    /**
     * 异步通知地址
     */
    @Value("${pay.alipay.notify_url}")
    private String notifyUrl;
    /**
     * 跳转通知地址
     */
    @Value("${pay.alipay.return_url}")
    private String returnUrl;
    /**
     * 商品金额
     */
    @Value("${pay.alipay.price}")
    private BigDecimal price;
    /**
     * 商品金额
     */
    @Value("${pay.alipay.is_html}")
    private Integer isHtml;


    /**
     * 通讯密钥
     */
    @Value("${pay.alipay.key}")
    private String key;


    public static String URL;
    public static Integer UID;
    public static String NOTIFY_URL;
    public static String RETURN_URL;
    public static BigDecimal PRICE;
    public static String KEY;
    public static Integer IS_HTML;

    @Override
    public void afterPropertiesSet() throws Exception {
        URL = url;
        UID = uid;
        NOTIFY_URL = notifyUrl;
        RETURN_URL = returnUrl;
        PRICE = price;
        KEY = key;
        IS_HTML = isHtml;
    }
}
