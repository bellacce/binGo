package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.project.biz.service.OrdersRebateService;
import com.wenhui.project.biz.service.StoreUserService;
import com.wenhui.project.dal.mybatis.dao.OrdersRebateMapper;
import com.wenhui.project.dal.mybatis.dataobject.OrdersRebate;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单利润分成 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-18
 */
@Service
public class OrdersRebateServiceImpl extends ServiceImpl<OrdersRebateMapper, OrdersRebate> implements OrdersRebateService {

    @Autowired
    private StoreUserService storeUserService;

    @Override
    public BigDecimal getRebateById(String startTime, String endTime) {
        int userId = UserThreadLocal.get().getUserId().intValue();
        BigDecimal reduce = getUserRebateById(userId, startTime, endTime);
        reduce = reduce.add(getUserSpreadRebateById(userId, startTime, endTime)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return reduce;
    }

    @Override
    public BigDecimal getUserRebateById(Integer userId, String startTime, String endTime) {
        List<OrdersRebate> ordersRebates = this.selectList(new EntityWrapper<OrdersRebate>()
                .eq("state", 0)
                .eq("user_id", userId)
                .ge(StringUtils.isNotEmpty(startTime), "create_time", startTime)
                .le(StringUtils.isNotEmpty(endTime), "create_time", endTime)
        );
        BigDecimal reduce = ordersRebates.stream().map(OrdersRebate::getUserRebate).reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce;
    }
    @Override
    public BigDecimal getTerraceRebate(Integer type, String startTime, String endTime) {
        List<OrdersRebate> ordersRebates = this.selectList(new EntityWrapper<OrdersRebate>()
                .eq("state", 0)
                .ge(StringUtils.isNotEmpty(startTime), "create_time", startTime)
                .le(StringUtils.isNotEmpty(endTime), "create_time", endTime)
        );
        BigDecimal reduce = new BigDecimal(0);
        if (type==0){
            reduce = ordersRebates.stream().map(OrdersRebate::getTerraceRebate).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        if (type==1){
            reduce = ordersRebates.stream().map(OrdersRebate::getUserRecommendRebate).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return reduce;
    }

    @Override
    public BigDecimal getUserSpreadRebateById(Integer userId, String startTime, String endTime) {
        BigDecimal reduce = new BigDecimal(0);
        List<StoreUser> storeUsers = storeUserService.selectList(new EntityWrapper<StoreUser>().eq("recommend_id", userId));
        List<Integer> userIds = storeUsers.stream().map(StoreUser::getUid).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userIds)){
            List<OrdersRebate> ordersRecommendRebates = this.selectList(new EntityWrapper<OrdersRebate>()
                    .eq("state", 0)
                    .in("user_id", userIds).ge(StringUtils.isNotEmpty(startTime), "create_time", startTime)
                    .le(StringUtils.isNotEmpty(endTime), "create_time", endTime)
            );
            reduce = ordersRecommendRebates.stream().map(OrdersRebate::getUserRecommendRebate).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return reduce;
    }


}
