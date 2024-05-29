package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.OrdersService;
import com.wenhui.project.web.dto.AdminOrderListDto;
import com.wenhui.project.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户订单 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/storeOrder")
@Api(tags = "后台用户订单")
public class AdminStoreOrderController {
    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value = "首页获取所有待处理订单")
    @PostMapping("/all/order/detail")
    @PassToken
    public CommonRestResult<GetAllOrderDetailVo> getAllOrderDetail() {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.getAllOrderDetail();
        });
    }

    @ApiOperation(value = "获取运营数据")
    @PostMapping("/operate/data")
    @PassToken
    public CommonRestResult<GetOperateDataVo> getOperateData(@RequestParam String startTime, @RequestParam String endTime) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.getOperateData(startTime, endTime);
        });
    }

    @ApiOperation(value = "获取用户订单数据")
    @PostMapping("/order/list")
    @PassToken
    public CommonRestResult<AdminOrderListVo> getOrderList(@RequestParam String user_id, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.getOrderList(user_id, startTime, endTime);
        });
    }

    @ApiOperation(value = "盲盒订单列表")
    @PostMapping("/searchBoxOrder")
    @PassToken
    public CommonRestResult<Page<AdminOrderBoxListVo>> searchBoxOrder(@RequestBody AdminOrderListDto adminOrderListDto) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.searchBoxOrder(adminOrderListDto);
        });
    }


    @ApiOperation(value = "盲盒子订单列表")
    @PostMapping("/searchBoxOrderDetail")
    @PassToken
    public CommonRestResult<List<AdminBoxOrderDetailVo>> searchBoxOrderDetail(@RequestParam(required = true,value = "orderId") String orderId) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.searchBoxOrderDetail(orderId);
        });
    }

    @ApiOperation(value = "发货接口")
    @PostMapping("/changeOrderToSended")
    @PassToken
    public CommonRestResult<Boolean> changeOrderToSended(@RequestBody DeliveryVo deliveryVo) {
        return RestBusinessTemplate.execute(() -> {
           Boolean result=ordersService.changeOrderToSended(deliveryVo);
            return result;
        });
    }


}

