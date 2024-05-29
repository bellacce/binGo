package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.Orders;
import com.wenhui.project.web.dto.AdminOrderBoxListDto;
import com.wenhui.project.web.dto.AdminOrderListDto;
import com.wenhui.project.web.dto.OrdersListDto;
import com.wenhui.project.web.vo.AdminOrderBoxVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户订单总表 Mapper 接口
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
public interface OrdersMapper extends BaseMapper<Orders> {


    /**
     * 查询订单列表
     *
     * @param typea
     * @return
     */
    List<OrdersListDto> queryList(@Param(value = "typea") Integer typea, @Param(value = "uid") Integer uid);


    List<AdminOrderBoxListDto> searchBoxOrder(AdminOrderListDto adminOrderListDto);


    AdminOrderBoxVo searchBoxOrderDetail(@Param(value = "orderId") String orderId);
}
