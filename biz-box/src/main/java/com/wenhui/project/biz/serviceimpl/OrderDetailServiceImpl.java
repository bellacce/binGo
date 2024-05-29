package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.OrderDetailService;
import com.wenhui.project.dal.mybatis.dao.OrderDetailMapper;
import com.wenhui.project.dal.mybatis.dataobject.OrderDetail;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
