package com.wenhui.integration.cos;

import lombok.Data;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 临时密钥获取实体
 @create 2023/2/22
*/
@Data
public class TmpSecretKeyConstant {

    private String tmpSecretId;
    private String tmpSecretKey;
    private String sessionToken;

    public TmpSecretKeyConstant(String tmpSecretId, String tmpSecretKey, String sessionToken) {
        this.tmpSecretId = tmpSecretId;
        this.tmpSecretKey = tmpSecretKey;
        this.sessionToken = sessionToken;
    }
}
