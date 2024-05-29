package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.web.dto.GoodsPromotionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/storeGoods")
@Api(tags = "商品中心")
public class StoreGoodsController {

    @Autowired
    private StoreGoodsService storeGoodsService;

    @GetMapping("/goods/promotion/{type}")
    @ApiOperation(value = "商品活动专区列表 查询  type传1为查询前15个，传空为查询所有")
    public CommonRestResult<Boolean> goodsPromotion(@ApiParam("type传1为查询前15个，传空为查询所有") @PathVariable Integer type) {
        return RestBusinessTemplate.execute(() -> {
            List<GoodsPromotionDto> goodsPromotionDto = storeGoodsService.goodsPromotion(type);
            return goodsPromotionDto;
        });
    }

    @GetMapping("/query/poductDetails/{goodsId}")
    @ApiOperation(value = "单个商品详细信息查询")
    public CommonRestResult<Boolean> productDetails(@ApiParam("商品id") @PathVariable Integer goodsId) {
        return RestBusinessTemplate.execute(() -> {
            List<GoodsPromotionDto> goodsPromotionDto = storeGoodsService.productDetails(goodsId);
            return goodsPromotionDto;
        });
    }
}

