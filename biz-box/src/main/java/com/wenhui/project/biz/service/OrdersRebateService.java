package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersRebate;

import java.math.BigDecimal;

/**
 * <p>
 * 订单利润分成 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-18
 */
public interface OrdersRebateService extends IService<OrdersRebate> {

    /**
     * 登录的用户收益查看
     * @return
     */
    BigDecimal getRebateById(String startTime, String endTime);
    /**
     * 登录的用户收益查看
     * @return
     */
    BigDecimal getUserRebateById(Integer uid, String startTime, String endTime);
    /**
     * 登录的用户收益查看
     * @return
     */
    BigDecimal getTerraceRebate(Integer type, String startTime, String endTime);
    /**
     * 登录的用户收益查看
     * @return
     */
    BigDecimal getUserSpreadRebateById(Integer uid, String startTime, String endTime);
}
