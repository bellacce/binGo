package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreBlindboxGoodsService;
import com.wenhui.project.web.dto.AdminBoxGoodsAddDto;
import com.wenhui.project.web.dto.AdminBoxGoodsDetailDto;
import com.wenhui.project.web.vo.AdminBoxGoodsDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 商品评论 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/storeBoxGoods")
@Api(tags = "后台盲盒商品管理")
public class AdminStoreBlindBoxGoodsController {

    @Autowired
    private StoreBlindboxGoodsService storeBlindboxGoodsService;

    @PostMapping("/goods/list")
    @ApiOperation(value = "获取盲盒对应的商品")
    public CommonRestResult<Page<AdminBoxGoodsDetailVo>> getBoxDetail(@RequestBody AdminBoxGoodsDetailDto adminBoxGoodsDetailDto ) {
        return RestBusinessTemplate.execute(() -> {
            Page<AdminBoxGoodsDetailVo> resut = storeBlindboxGoodsService.getBoxDetail(adminBoxGoodsDetailDto);
            return resut;
        });
    }

    @PostMapping("/goods/add")
    @ApiOperation(value = "添加/修改盲盒商品")
    public CommonRestResult<Boolean> addBoxShop(@RequestBody @Valid AdminBoxGoodsAddDto adminBoxGoodsDetailDto ) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeBlindboxGoodsService.addBoxShop(adminBoxGoodsDetailDto);
            return resut;
        });
    }

    @PostMapping("/goods/delete")
    @ApiOperation(value = "删除盲盒商品")
    public CommonRestResult<Boolean> deleteBoxShop(@RequestParam Integer boxId, @RequestParam Integer id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeBlindboxGoodsService.deleteBoxShop(boxId, id);
            return resut;
        });
    }
}

