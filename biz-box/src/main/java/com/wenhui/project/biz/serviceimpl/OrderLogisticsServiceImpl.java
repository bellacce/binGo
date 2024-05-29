package com.wenhui.project.biz.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.project.biz.service.OrderDetailService;
import com.wenhui.project.biz.service.OrderLogisticsService;
import com.wenhui.project.dal.mybatis.dao.OrderLogisticsMapper;
import com.wenhui.project.dal.mybatis.dataobject.OrderDetail;
import com.wenhui.project.dal.mybatis.dataobject.OrderLogistics;
import com.wenhui.project.web.vo.ShippingInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单物流表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {

    @Resource
    private OrderDetailService orderDetailService;


    /**
     * 后台发货接口获取收件人信息以及需要发货的商品
     *
     * @param orderId
     * @return
     */
    @Override
    public ShippingInfoVo queryConsigneeInfo(Integer orderId) {
        ShippingInfoVo shippingInfoVo = new ShippingInfoVo();
        OrderLogistics orderLogistics = this.selectOne(new EntityWrapper<OrderLogistics>().eq("order_id", orderId).eq("delete_flag", 0));
        if (BeanUtil.isEmpty(orderLogistics))
            throw new BusinessException("4000011", "订单收件信息不存在");
        StringBuffer stringBuffer = new StringBuffer()
                .append(orderLogistics.getConsigneeRealname()).append(",")
                .append(orderLogistics.getConsigneeTelphone()).append(",")
                .append(orderLogistics.getConsigneeAddress());
        shippingInfoVo.setConsigneeInfo(stringBuffer.toString());

        OrderDetail orderDetail = orderDetailService.selectOne(new EntityWrapper<OrderDetail>().eq("order_id", orderId).eq("delete_flag", 0));
        if (BeanUtil.isEmpty(orderDetail))
            throw new BusinessException("4000011", "未获取到开盒以后的信息");
        BeanUtils.copyProperties(orderDetail,shippingInfoVo);
        return shippingInfoVo;
    }
}
