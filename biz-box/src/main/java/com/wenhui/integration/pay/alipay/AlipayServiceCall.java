package com.wenhui.integration.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.biz.service.OrdersPayRequestLogService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;
import com.wenhui.project.web.dto.OrderPaymentCallDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 支付乐服务调用
 @create 2023/2/10
*/
@Data
@Slf4j
public class AlipayServiceCall {

    @Resource
    private OrdersPayRequestLogService ordersPayRequestLogService;

    public List AlipaySubmit(OrderPaymentCallDto orderPaymentCallDto, LogSaveService logSaveService) {
        AlipayServiceResponse alipayServiceResponse = new AlipayServiceResponse();
        orderPaymentCallDto.setPayId("20233616013624938");
        try {
            orderPaymentCallDto.setNotifyUrl(AlipayServiceCallConstant.NOTIFY_URL)
                    .setUid(AlipayServiceCallConstant.UID)
                    .setReturnUrl(AlipayServiceCallConstant.RETURN_URL)
                    .setPrice(AlipayServiceCallConstant.PRICE)
                    .setParam("testa")
                    .setIsHtml(AlipayServiceCallConstant.IS_HTML);
//        String sign = orderPaymentCallDto.getPayId()+(Objects.nonNull(orderPaymentCallDto.getParam())?orderPaymentCallDto.getParam():"")+orderPaymentCallDto.getType()+orderPaymentCallDto.getPrice()+AlipayServiceCallConstant.KEY;
            String sign = orderPaymentCallDto.getPayId() + orderPaymentCallDto.getParam() + orderPaymentCallDto.getType() + orderPaymentCallDto.getPrice() + AlipayServiceCallConstant.KEY;
            String md5 = DigestUtils.md5Hex(sign);
            orderPaymentCallDto.setSign(md5);
//            alipayServiceResponse = CustomHttpClient.doJsonPost(AlipayServiceCallConstant.URL, orderPaymentCallDto, new TypeReference<AlipayServiceResponse>() {});
            alipayServiceResponse.setCode(0);
            ArrayList<JSONObject> objects = new ArrayList<>();
            objects.add(JSONObject.parseObject("{\"date\":1676525787,\"uid\":3697,\"payType\":1,\"orderId\":\"202302161302276186\",\"price\":0.01,\"outTradeNo\":\"202302161302276186\",\"reallyPrice\":\"0.01\",\"isAuto\":1,\"payUrl\":\"wxp://f2f0TVzKge5GvD0giekm1o-I7un5yFFd3YoPYg--0vcKNx9wW_yD6UJEexeaRuH6kQne\",\"payId\":\"20233616013624938\",\"state\":0,\"timeOut\":3}"));
            alipayServiceResponse.setData(objects);
            alipayServiceResponse.setCode(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logSaveService.saveLog(new OrdersPayRequestLog(AlipayServiceCallConstant.URL, JSONObject.toJSONString(orderPaymentCallDto), JSONObject.toJSONString(alipayServiceResponse), 1));
        }
        if (Objects.nonNull(alipayServiceResponse.getCode()) && alipayServiceResponse.getCode() == -1) {
            log.info("支付失败," + alipayServiceResponse.getMsg());
            throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "拉起支付失败，请重新购买！");
        }
        return alipayServiceResponse.getData();
    }
}
