package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.OrdersPayRequestLogService;
import com.wenhui.project.dal.mybatis.dao.OrdersPayRequestLogMapper;
import com.wenhui.project.dal.mybatis.dataobject.OrdersPayRequestLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付回调日志记录 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Service
public class OrdersPayRequestLogServiceImpl extends ServiceImpl<OrdersPayRequestLogMapper, OrdersPayRequestLog> implements OrdersPayRequestLogService {
    @Override
    public boolean saveLog(OrdersPayRequestLog var1) {
        return this.insert(var1);
    }
}
