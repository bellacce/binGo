package com.wenhui.project.biz.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.enums.PayTyprEnum;
import com.wenhui.common.base.utils.QRCodeUtil;
import com.wenhui.core.base.utils.RandomUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.integration.pay.alipay.AlipayServiceCall;
import com.wenhui.project.biz.service.OrdersPayRequestLogService;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.biz.service.StoreOrderService;
import com.wenhui.project.dal.mybatis.dao.StoreOrderMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreOrder;
import com.wenhui.project.web.dto.OrderPaymentCallDto;
import com.wenhui.project.web.dto.OrderPaymentCallResponseDto;
import com.wenhui.project.web.dto.OrderPaymentStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户订单 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderMapper, StoreOrder> implements StoreOrderService {

    @Autowired
    private StoreGoodsService storeGoodsService;
    @Resource
    private OrdersPayRequestLogService ordersPayRequestLogService;

    private SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyymmddhhmmss");

    /**
     * @param type
     * @return
     * @Override 1:支付宝
     * 2:微信支付
     * 3:支付宝当面付
     * 4:QQ钱包
     */
    public Boolean orderPaymentCall(HttpServletRequest request, HttpServletResponse response, String type) {
        OrderPaymentCallDto orderPaymentCallDto = new OrderPaymentCallDto();
        String datetime = yyyymmddhhmmss.format(new Date());
        orderPaymentCallDto.setPayId(datetime + RandomUtils.generateNumberRandom(3));
        switch (type) {
            case "1":
                orderPaymentCallDto.setType(PayTyprEnum.ALIPAY.getCode());
                break;
            case "2":
                orderPaymentCallDto.setType(PayTyprEnum.WXPAY.getCode());
                break;
            case "3":
                orderPaymentCallDto.setType(PayTyprEnum.DMF.getCode());
                break;
            case "4":
                orderPaymentCallDto.setType(PayTyprEnum.QQPAY.getCode());
                break;
            default:
                throw new BusinessException(String.valueOf(ErrorCode.METHOD_NOT_ALLOWED), "支付方式错误");
        }
        List payResult = new AlipayServiceCall().AlipaySubmit(orderPaymentCallDto, ordersPayRequestLogService);
        //[{"date":1676525787,"uid":3697,"payType":1,"orderId":"202302161302276186","price":0.01,"outTradeNo":"202302161302276186","reallyPrice":"0.01","isAuto":1,"payUrl":"wxp://f2f0TVzKge5GvD0giekm1o-I7un5yFFd3YoPYg--0vcKNx9wW_yD6UJEexeaRuH6kQne","payId":"20233616013624938","state":0,"timeOut":3}]
        if (CollectionUtils.isEmpty(payResult)) {
            throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
        }
        OrderPaymentCallResponseDto orderPaymentCallResponseDto = JSON.parseObject(payResult.get(0).toString(), OrderPaymentCallResponseDto.class);
        String payUrl = orderPaymentCallResponseDto.getPayUrl();
        QRCodeUtil.getQRcodeStream(response, payUrl, null);
        return true;
    }

    @Override
    public Boolean orderPaymentStatus(OrderPaymentStatusDto order) {
        if (Objects.nonNull(order)) {
            String payId = order.getPayId();
//            this.selectOne(new EntityWrapper<>().eq("",""))
        }

        return null;
    }
}
