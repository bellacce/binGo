package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;

/**
 * <p>
 * 支付回调日志记录 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
public interface OrdersPayRequestLogService extends IService<OrdersPayRequestLog>, LogSaveService<OrdersPayRequestLog> {

}
