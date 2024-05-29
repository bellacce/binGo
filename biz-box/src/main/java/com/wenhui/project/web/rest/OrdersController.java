package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.OrdersService;
import com.wenhui.project.web.dto.OneOrderDetailDto;
import com.wenhui.project.web.dto.OrderPaymentStatusDto;
import com.wenhui.project.web.dto.OrderResultDto;
import com.wenhui.project.web.dto.PickupGoodsDto;
import com.wenhui.project.web.vo.OrderInfoVo;
import com.wenhui.project.web.vo.OrderRollListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户订单总表 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@RestController
@RequestMapping("/orders")
@Api(tags = "用户订单")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value = "支付宝异步回调地址")
    @PostMapping("/alipay/notify/")
    public void alipayNotify(HttpServletResponse response,HttpServletRequest request) {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(name, valueStr.toString());
        }

        boolean verifyResult = ordersService.alipayVerify(params);
        try {
            if (verifyResult){
                response.getWriter().write("success");
            }else{
                response.getWriter().write("fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @ApiOperation(value = "查询单个订单信息")
    @GetMapping("/order/queryOrder/{orderId}")
    @PassToken
    public CommonRestResult<OneOrderDetailDto> queryOrder(@ApiParam(value = "订单id", defaultValue = "1") @PathVariable(value = "orderId", required = true) Integer orderId) {
        return RestBusinessTemplate.execute(() -> {
            OneOrderDetailDto oneOrderDetailDto = ordersService.queryOneOrder(orderId);
            return oneOrderDetailDto;
        });
    }

    @ApiOperation(value = "提交提货信息接口")
    @PostMapping("/order/insertPickupGoods/")
    @PassToken
    public CommonRestResult<Boolean> insertPickupGoods(@RequestBody Map<String, Object> requestBody) {
        return RestBusinessTemplate.execute(() -> {
            Integer orderId = (Integer) requestBody.get("orderId");
            Integer addressId = (Integer) requestBody.get("addressId");
            if (orderId==null&&addressId==null){
                return false;
            }
            Boolean result = ordersService.insertPickupGoods(orderId, addressId);
            return result;
        });
    }

    @ApiOperation(value = "去提货界面相关信息")
    @GetMapping("/order/pickupGoods/{orderNo}")
    @PassToken
    public CommonRestResult<PickupGoodsDto> pickupGoods(@ApiParam(value = "订单id", defaultValue = "1") @PathVariable(value = "orderNo", required = true) String orderNo) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.pickupGoods(orderNo);
        });
    }


    @ApiOperation(value = "订单列表")
    @GetMapping("/order/list/")
    @PassToken
    public CommonRestResult<Boolean> ordersList(@ApiParam("订单状态") @RequestParam(value = "type", required = false) Integer type) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.ordersList(type);
        });
    }

    /**
     * @param type
     * @return
     * @PostMapping("/order/paymentcall") * 1:支付宝
     * * 2:微信支付
     * * 3:支付宝当面付
     * * 4:QQ钱包
     */
    @ApiOperation(value = "商品支付 新增")
    @GetMapping("/order/pay")
    @PassToken
    public CommonRestResult orderPaymentCall(HttpServletRequest request, HttpServletResponse response, @RequestParam String type, @RequestParam Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            ordersService.orderPaymentCall(request, response, type, boxId);
            return true;
        });
    }

    @ApiOperation(value = "商品支付 新增")
    @PostMapping("/order/orderPay")
    @PassToken
    public CommonRestResult<OrderResultDto> orderPay(HttpServletRequest request,@RequestBody OrderInfoVo orderInfo) {
        return RestBusinessTemplate.execute(() -> {
            OrderResultDto orderResultDto = ordersService.placeOrder(request,orderInfo);
            return orderResultDto;
        });
    }



    @ApiOperation(value = "订单支付状态 查询")
    @GetMapping("/order/pay/status")
    @PassToken
    public CommonRestResult<Boolean> orderPayStatus(@RequestParam String orderNo) {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.orderPayStatus(orderNo);
        });
    }

    @ApiOperation(value = "支付状态修改 异步通知接口")
    @GetMapping("/pay/status")
    public void orderPaymentStatus(HttpServletResponse response,@RequestParam Map<String,String> order) {
        Boolean result = ordersService.orderPaymentStatus(order);
        try {
            if (result){
                response.getWriter().write("success");
            }else{
                response.getWriter().write("error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ApiOperation(value = "yzf支付状态修改 异步通知接口")
    @GetMapping("/pay/yzf/status")
    public void orderPaymentStatusYzf(HttpServletResponse response,@RequestParam Map<String,String> order) {
        Boolean result = ordersService.orderPaymentStatusYzf(order);
        try {
            if (result){
                response.getWriter().write("success");
            }else{
                response.getWriter().write("error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @ApiOperation(value = "获取被开通的滚动商品列表 查询")
    @GetMapping("/order/roll/list")
    public CommonRestResult<List<OrderRollListVo>> orderRollList() {
        return RestBusinessTemplate.execute(() -> {
            return ordersService.orderRollList();
        });
    }
}

