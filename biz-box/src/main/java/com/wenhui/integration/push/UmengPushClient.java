package com.wenhui.integration.push;

import com.wenhui.core.core.web.CommonRestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*import org.springframework.cloud.netflix.feign.FeignClient;*/

/*
@FeignClient(name = "codeblue-external-gateway", url = "http://123.45.678.910:8888/", fallback = UmengPushClientFallback.class)
*/
public interface UmengPushClient {

    @PostMapping(value = "/services/push/umeng/devicetoken/addToken")
    CommonRestResult addToken(@RequestBody TokenForm form);

}
