package com.wenhui.project.web.rest.manager;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.OrderLogisticsService;
import com.wenhui.project.web.vo.ShippingInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("manager/orderLogistics")
@Api(tags = "发货信息接口")
public class AdminOrderLogisticsController {

    @Resource
    OrderLogisticsService orderLogisticsService;

    @GetMapping("/queryConsigneeInfo")
    @PassToken
    @ApiOperation(value = "获取发货信息接口")
    public CommonRestResult<ShippingInfoVo> queryConsigneeInfo(@RequestParam Integer orderId) {
        return RestBusinessTemplate.execute(() -> {
            ShippingInfoVo resut = orderLogisticsService.queryConsigneeInfo(orderId);
            return resut;
        });
    }
}
