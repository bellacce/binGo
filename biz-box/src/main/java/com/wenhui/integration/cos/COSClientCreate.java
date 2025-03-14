package com.wenhui.integration.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.region.Region;

/*
 @author 天赋吉运-bms
 @DESCRIPTION cos客户端
 @create 2023/2/22
*/
public class COSClientCreate {
    public COSClient getCosClient(TmpSecretKeyConstant tmpSecretKeyConstant) {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = tmpSecretKeyConstant.getTmpSecretId();
        String tmpSecretKey = tmpSecretKeyConstant.getTmpSecretKey();
        String sessionToken = tmpSecretKeyConstant.getSessionToken();
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的地域
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
        Region region = new Region("ap-nanjing"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }
}
