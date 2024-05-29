package com.wenhui.integration.pay.alipay;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 支付乐响应实体
 @create 2023/2/10
*/

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AlipayServiceResponse {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 数据
     */
    private List data;
}
