package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.OrdersRebateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 订单利润分成 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-18
 */
@RestController
@RequestMapping("/ordersRebate")
@Api(tags = "用户收益")
public class OrdersRebateController {

    @Autowired
    private OrdersRebateService ordersRebateService;

    @ApiOperation(value = "登录的用户收益查看")
    @GetMapping("/rebate/userRebate")
    @PassToken
    public CommonRestResult<BigDecimal> getRebateById() {
        return RestBusinessTemplate.execute(() -> {
            return ordersRebateService.getRebateById(null,null);
        });
    }
}

