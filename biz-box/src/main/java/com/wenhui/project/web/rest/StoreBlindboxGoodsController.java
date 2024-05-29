package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreBlindboxGoodsService;
import com.wenhui.project.web.dto.BoxGoodsBriefDescDto;
import com.wenhui.project.web.dto.StoreBlindboxGoodsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/storeBlindboxGoods")
@Api(tags = "盲盒商品表")
public class StoreBlindboxGoodsController {


    @Resource
    private StoreBlindboxGoodsService storeBlindboxGoodsService;

    @GetMapping("/query/{boxId}")
    @ApiOperation(value = "根据盲盒id获取盲盒内的商品列表")
    public CommonRestResult<StoreBlindboxGoodsDto> queryBoxGoodsList(@ApiParam("盲盒id") @PathVariable Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            return storeBlindboxGoodsService.queryGoodsList(boxId);
        });
    }

    @GetMapping("/queryGoods/{Id}")
    @ApiOperation(value = "（点击查看全部调用）根据商品id获取商品简要信息")
    public CommonRestResult<BoxGoodsBriefDescDto> queryGoods(@ApiParam("商品id") @PathVariable Integer Id) {
        return RestBusinessTemplate.execute(() -> {
            return storeBlindboxGoodsService.queryGoods(Id);
        });
    }

}

