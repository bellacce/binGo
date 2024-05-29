package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreActivityService;
import com.wenhui.project.web.dto.StoreActivityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 活动管理 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/storeActivity")
@Api(tags = "活动管理")
public class StoreActivityController {

    @Autowired
    private StoreActivityService storeActivityService;


    @GetMapping(value = "/get")
    @ApiOperation(value = "获取全部顶部活动横幅")
    public CommonRestResult<StoreActivityDto> queryall() {
        return RestBusinessTemplate.execute(() -> {
            List<StoreActivityDto> storeActivities = storeActivityService.queryList();
            return storeActivities;
        });
    }


}

