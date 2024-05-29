package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.web.dto.GoodUpdateDto;
import com.wenhui.project.web.dto.GoodsListDto;
import com.wenhui.project.web.vo.GoodsListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/storeGoods")
@Api(tags = "商品中心")
public class AdminStoreGoodsController {

    @Autowired
    private StoreGoodsService storeGoodsService;

    @PostMapping("/list/goods")
    @ApiOperation(value = "获取商品列表")
    public CommonRestResult<Page<GoodsListVo>> getShopList(@RequestBody GoodsListDto goodsListDto) {
        return RestBusinessTemplate.execute(() -> {
            Page<GoodsListVo> resut = storeGoodsService.getShopList(goodsListDto);
            return resut;
        });
    }

    @PostMapping("/add/good")
    @ApiOperation(value = "新增商品")
    public CommonRestResult<Boolean> addShopInfo(@RequestBody @Valid GoodUpdateDto goodUpdateDto) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeGoodsService.addShopInfo(goodUpdateDto);
            return resut;
        });
    }


    @PostMapping("/update/status")
    @ApiOperation(value = "商品上下架")
    public CommonRestResult<Boolean> changeShopStatus(@RequestParam String id, @RequestParam  Boolean status) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeGoodsService.changeShopStatus(id, status);
            return resut;
        });
    }

    @PostMapping("/delete/good")
    @ApiOperation(value = "删除商品")
    public CommonRestResult<Boolean> deleteShopList(@RequestParam String id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeGoodsService.deleteShopList(id);
            return resut;
        });
    }
}

