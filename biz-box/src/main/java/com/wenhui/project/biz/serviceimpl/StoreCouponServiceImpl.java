package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreCoupon;
import com.wenhui.project.dal.mybatis.dao.StoreCouponMapper;
import com.wenhui.project.biz.service.StoreCouponService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券模板表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreCouponServiceImpl extends ServiceImpl<StoreCouponMapper, StoreCoupon> implements StoreCouponService {

}
