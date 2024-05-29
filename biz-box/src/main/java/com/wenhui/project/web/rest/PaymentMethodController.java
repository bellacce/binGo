package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.PaymentMethodService;
import com.wenhui.project.web.dto.StoreActivityDto;
import com.wenhui.project.web.dto.StoreBlindBoxDto;
import com.wenhui.project.web.vo.PayListPayListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 支付方式 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/paymentMethod")
@Api(tags = "支付方式")
public class PaymentMethodController {

    @Resource
    PaymentMethodService paymentMethodService;

    @GetMapping(value = "/payList")
    @ApiOperation(value = "首页盲盒推荐列表")
    @PassToken
    public CommonRestResult<StoreActivityDto> queryPayList() {
        return RestBusinessTemplate.execute(() -> {
            List<PayListPayListVo> storeBlindBoxDtos = paymentMethodService.queryPayList();
            return storeBlindBoxDtos;
        });
    }

}

