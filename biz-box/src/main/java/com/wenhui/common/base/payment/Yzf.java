package com.wenhui.common.base.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.integration.pay.alipay.AlipayServiceCallConstant;
import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.PayParamDto;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class Yzf extends Payment{


    // 商户ID，由支付分配
    private static  String MERCHANT_ID = "your_merchant_id";

    // 商户密钥，由支付分配
    private static String KEY = "your_key";

    // 支付网关API URL
    private static  String API_URL = "https://api.yiji.com/gateway.html";

    @Override
    public PaymentResultBo pay(PayParamDto payParamDto, LogSaveService logSaveService) {
        // 商户ID，由支付分配
        MERCHANT_ID = payParamDto.getUid();

        // 商户密钥，由支付分配
        KEY = payParamDto.getPrivateKey();

        // 支付网关API URL
        API_URL = payParamDto.getChannelUrl();

        // 构造请求参数
        Map<String, String> params = new TreeMap<>();
//        params.put("service", "create_direct_pay_by_user");
//        params.put("_input_charset", "UTF-8");
        params.put("pid", MERCHANT_ID);
        params.put("type", payParamDto.getPayType());
        params.put("out_trade_no", payParamDto.getOrderNo());
        params.put("notify_url", payParamDto.getNotifyUrl());
        params.put("return_url", payParamDto.getReturnUrl());
        params.put("name", payParamDto.getTradeName());
        params.put("money", payParamDto.getPrice().stripTrailingZeros().toString());
        params.put("clientip", payParamDto.getClientIp());
        params.put("param", "United States of America");
        //非必传
        params.put("device", "mobile");
        PaymentResultBo paymentResultBo = new PaymentResultBo();
        try {
            // 生成签名
            String sign = generateSignature(params);

            // 添加签名到请求参数中
            params.put("sign", sign);
            params.put("sign_type", "MD5");

            // 发送HTTP请求并获取响应结果
            String response = sendHttpRequest(buildRequestUrl(API_URL, params));
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject==null){
                throw new BusinessException("2321120","请求结果异常");
            }
            Integer code =Integer.valueOf(jsonObject.getString("code").toString());
            if (code!=null&&code==1){
                paymentResultBo.setCode(10000);
                paymentResultBo.setResultType(1);
                jsonObject.put("sign",sign);
                paymentResultBo.setResquestInfo(jsonObject.getString("payurl"));
                paymentResultBo.setJson(jsonObject);
                return paymentResultBo;
            }else {
                paymentResultBo.setCode(10001);
                paymentResultBo.setResultType(0);
                paymentResultBo.setJson(jsonObject);
                return paymentResultBo;
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("2321121",e.getMessage());
        }finally {
            logSaveService.saveLog(new OrdersPayRequestLog(AlipayServiceCallConstant.URL, JSONObject.toJSONString(params), JSONObject.toJSONString(paymentResultBo), 1));
        }
    }

    /**
     * 生成签名
     */
    private static String generateSignature(Map<String, String> params) throws Exception {
        // 按照参数名字典排序
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!value.isEmpty()){
            sb.append(key).append("=").append(value);
                if (i < params.size() - 1) {
                    sb.append("&");
                }
                i++;
            }
        }
        sb.append(KEY);
//        sb.append("key=").append(KEY);

        // 使用MD5加密
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(sb.toString().getBytes("UTF-8"));
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

    /**
     * 构造请求URL
     */
    private static String buildRequestUrl(String apiUrl, Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder(apiUrl);
        sb.append("?").append(getQueryString(params));
        return sb.toString();
    }

    /**
     * 将参数转换为URL查询字符串格式
     */
    private static String getQueryString(Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8")).append("&");
        }
        return sb.toString();
    }

    /**
     * 发送HTTP请求并获取响应结果
     */
    private static String sendHttpRequest(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();

        return sb.toString();
    }
}
