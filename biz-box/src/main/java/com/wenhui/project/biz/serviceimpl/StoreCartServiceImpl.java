package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreCart;
import com.wenhui.project.dal.mybatis.dao.StoreCartMapper;
import com.wenhui.project.biz.service.StoreCartService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreCartServiceImpl extends ServiceImpl<StoreCartMapper, StoreCart> implements StoreCartService {

}
