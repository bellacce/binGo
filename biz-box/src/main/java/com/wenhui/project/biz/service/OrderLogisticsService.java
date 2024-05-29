package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.OrderLogistics;
import com.wenhui.project.web.vo.ShippingInfoVo;

import java.util.List;

/**
 * <p>
 * 订单物流表 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
public interface OrderLogisticsService extends IService<OrderLogistics> {

    /**
     * 后台发货接口获取收件人信息以及需要发货的商品
     * @param orderId
     * @return
     */
    ShippingInfoVo queryConsigneeInfo(Integer orderId);
}
