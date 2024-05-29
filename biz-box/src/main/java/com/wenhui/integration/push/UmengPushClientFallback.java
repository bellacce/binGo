package com.wenhui.integration.push;

import com.wenhui.core.core.web.CommonRestResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UmengPushClientFallback implements UmengPushClient {

    @Override
    public CommonRestResult addToken(@RequestBody TokenForm form) {
        // TODO Auto-generated method stub
        return null;
    }

}
