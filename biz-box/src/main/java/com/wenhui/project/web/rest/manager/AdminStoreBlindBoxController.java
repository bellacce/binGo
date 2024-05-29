package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreBlindBoxService;
import com.wenhui.project.web.dto.AdminStoreBlindBoxDto;
import com.wenhui.project.web.dto.StoreActivityDto;
import com.wenhui.project.web.vo.AdminBoxListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * <p>
 * 盲盒配置表 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/storeBlindBox")
@Api(tags = "后台盲盒管理")
public class AdminStoreBlindBoxController {

    @Autowired
    private StoreBlindBoxService storeBlindBoxService;

    @PostMapping("/update/box")
    @ApiOperation(value = "新增/修改盲盒")
    public CommonRestResult<StoreActivityDto> updateBoxAllInfo(@RequestBody @Valid AdminStoreBlindBoxDto adminStoreBlindBoxDto) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeBlindBoxService.updateBoxAllInfo(adminStoreBlindBoxDto);
            return resut;
        });
    }

    @GetMapping("/test/profit/box")
    @ApiOperation(value = "10000次开盲盒收益测试")
    public CommonRestResult<BigDecimal> profitBox(@RequestParam Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            BigDecimal resut = storeBlindBoxService.testProfitBox(boxId);
            return resut;
        });
    }

    @PostMapping("/list/box")
    @ApiOperation(value = "获取盲盒列表")
    public CommonRestResult<Page<AdminBoxListVo>> getBoxList(@RequestBody PageDto pageDto) {
        return RestBusinessTemplate.execute(() -> {
            Page<AdminBoxListVo> resut = storeBlindBoxService.getBoxList(pageDto);
            return resut;
        });
    }

    @PostMapping("/box/used")
    @ApiOperation(value = "设置第二套概率是否启用")
    public CommonRestResult<Page<AdminBoxListVo>> changeProSettingUsed(@RequestParam Integer boxId,@RequestParam Integer type,@RequestParam boolean used) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeBlindBoxService.changeProSettingUsed(boxId,type,used);
            return resut;
        });
    }

    @PostMapping("/box/delete")
    @ApiOperation(value = "删除盲盒信息")
    public CommonRestResult<Page<AdminBoxListVo>> deleteBox(@RequestParam Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeBlindBoxService.deleteBox(boxId);
            return resut;
        });
    }

}

