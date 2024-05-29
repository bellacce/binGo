package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.WinningNotificationService;
import com.wenhui.project.web.dto.WinningNotificationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/winningNotification")
@Api(tags = "通知")
public class WinningNotificationController {

    @Autowired
    private WinningNotificationService winningNotificationService;

    @GetMapping(value = "/get/{typeid}")
    @ApiOperation(value = "获取中奖通知")
    public CommonRestResult<WinningNotificationDto> getNotic(@PathVariable Integer typeid) {
        return RestBusinessTemplate.execute(() -> {
            List<WinningNotificationDto> winningNotificationDtos = winningNotificationService.queryNotice(typeid);
            return winningNotificationDtos;
        });
    }
}

