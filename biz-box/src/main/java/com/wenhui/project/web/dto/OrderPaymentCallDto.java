package com.wenhui.project.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 订单支付调用Dto
 @create 2023/2/10
*/
@Data
@Accessors(chain = true)
public class OrderPaymentCallDto {
    /**
     * 商户ID
     */
    private Integer uid;
    /**
     * 异步通知地址
     */
    private String notifyUrl;
    /**
     * 跳转通知地址
     */
    private String returnUrl;
    /**
     * 支付方式
     */
    private Integer type;
    /**
     * 商户订单号
     */
    private String payId;
    /**
     * 商品金额
     */
    private BigDecimal price;
    /**
     * 签名字符串
     */
    private String sign;
    /**
     * 传入1则自动跳转到支付页面，否则返回创建结果的json数据
     */
    private Integer isHtml;
    /**
     * 传输参数，将会原样返回到异步和同步通知接口
     */
    private String param;
}
