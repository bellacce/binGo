package com.wenhui.integration.sms.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wh_shopbox
 * @description: 发送验证码
 * @author: Mr.Wang
 * @create: 2023-02-08 22:51
 **/
@Data
@Slf4j
public class LoginSMS {

    private final static String TENCENT_SMS_SUCCESS_CODE = "Ok";

    public Boolean tencentSendMessageToPhone(String verifyCode, String phone) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            Credential cred = new Credential(TencentSmsConstant.SECRET_ID, TencentSmsConstant.SECRET_KEY);
            // 实例化一个http选项
            HttpProfile httpProfile = new HttpProfile();
            // 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com
            httpProfile.setEndpoint(TencentSmsConstant.ENDPOINT);
            // 实例化一个client选项
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, TencentSmsConstant.REGION, clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            com.tencentcloudapi.sms.v20210111.models.SendSmsRequest req =
                    new com.tencentcloudapi.sms.v20210111.models.SendSmsRequest();
            // 设置发送短信的手机号（可以一次多个：{"18539344270", "13073775668"}）
            String[] phoneNumberSet1 = {phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            // 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
            req.setSmsSdkAppId(TencentSmsConstant.SMS_SDK_APP_ID);
            // 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
            req.setSignName(TencentSmsConstant.SIGN_NAME);
            // 模板 ID: 必须填写已审核通过的模板 ID
            req.setTemplateId(TencentSmsConstant.TEMPLATE_ID);

            // 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空
            // 我这里设置第一个参数为验证码，第二个为验证码超时时间
            String[] templateParamSet1 = {verifyCode, TencentSmsConstant.EXPIRED_TIME};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            com.tencentcloudapi.sms.v20210111.models.SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            log.info(com.tencentcloudapi.sms.v20210111.models.SendSmsResponse.toJsonString(resp));
            // 因为该需求是给一个手机号发送，发送结果是每个手机号的信息发送响应封装类(SendStatus)的数组，所以只需要该数组第一个参数即可
            return resp.getSendStatusSet()[0].getCode().equals(TENCENT_SMS_SUCCESS_CODE);
        } catch (TencentCloudSDKException e) {
            log.info(e.toString());
        }
        return false;
    }


}
