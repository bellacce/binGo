package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.Orders;
import com.wenhui.project.web.dto.*;
import com.wenhui.project.web.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户订单总表 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
public interface OrdersService extends IService<Orders> {

    void orderPaymentCall(HttpServletRequest request, HttpServletResponse response, String type, Integer boxId);

    /**
     * 下单接口
     * @param orderInfo
     * @return
     */
    OrderResultDto placeOrder(HttpServletRequest request,OrderInfoVo orderInfo);

    Boolean orderPayStatus(String orderNo);

    List<OrdersListDto> ordersList(Integer type);

    Boolean orderPaymentStatus(Map<String,String> order);

    /**
     * 去取货接口
     *
     * @param orderNo
     * @return
     */
    PickupGoodsDto pickupGoods(String orderNo);

    /**
     * 获取单个订单信息
     *
     * @param orderId
     * @return
     */
    OneOrderDetailDto queryOneOrder(Integer orderId);

    /**
     * 提交提货信息
     *
     * @param orderId
     * @param addressId
     * @return
     */
    Boolean insertPickupGoods(Integer orderId, Integer addressId);

    /**
     * 首页获取所有待处理订单
     *
     * @return
     */
    GetAllOrderDetailVo getAllOrderDetail();

    /**
     * 获取运营数据
     *
     * @return
     */
    GetOperateDataVo getOperateData(String startTime, String endTime);

    /**
     * 获取用户订单数据
     *
     * @return
     */
    AdminOrderListVo getOrderList(String user_id, String startTime, String endTime);

    /**
     * 获取用户订单列表
     * @param adminOrderListDto
     * @return
     */
    Page<AdminOrderBoxListVo> searchBoxOrder(AdminOrderListDto adminOrderListDto);


    /**
     * 获取子订单详细信息
     * @param orderId
     * @return
     */
    List<AdminBoxOrderDetailVo> searchBoxOrderDetail(String orderId);

    /**
     * 发货接口
     * @param deliveryVo
     * @return
     */
    Boolean changeOrderToSended(DeliveryVo deliveryVo);

    /**
     * 获取被开通的滚动商品列表
     * @return
     */
    List<OrderRollListVo> orderRollList();

    /**
     * Yzf回调
     * @param order
     * @return
     */
    Boolean orderPaymentStatusYzf(Map<String, String> order);

    /**
     * 支付宝支付成功回调，并校验签名是否一致
     * @param params
     * @return
     */
    boolean alipayVerify(Map<String, String> params);
}
