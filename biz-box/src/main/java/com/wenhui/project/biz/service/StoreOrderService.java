package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreOrder;
import com.wenhui.project.web.dto.OrderPaymentStatusDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户订单 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreOrderService extends IService<StoreOrder> {

    Boolean orderPaymentCall(HttpServletRequest request, HttpServletResponse response, String type);


    Boolean orderPaymentStatus(OrderPaymentStatusDto order);
}
