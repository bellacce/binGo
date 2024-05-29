package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.BaseConfigService;
import com.wenhui.project.web.dto.BaseConfigDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本配置 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/baseConfig")
@Api(tags = "后台配置")
public class BaseConfigController {

    @Autowired
    private BaseConfigService baseConfigService;

    @PostMapping("/addPhoto")
    @ApiOperation(value = "图片上传")
    public CommonRestResult<String> addPhoto(@RequestParam("image") MultipartFile file) {
        return RestBusinessTemplate.execute(() -> {
            String resut = baseConfigService.addPhoto(file);
//            return "https://whshopbox-1259121814.cos.ap-nanjing.myqcloud.com//shop/image/feedback/2023-02-27/146743089229505.jpg";
            return resut;
        });
    }

    @PostMapping("/addConfig")
    @ApiOperation(value = "添加配置")
    public CommonRestResult<Boolean> addConfig(@RequestBody BaseConfigDto baseConfigDto) {
        return RestBusinessTemplate.execute(() -> {
            boolean resut = baseConfigService.addConfig(baseConfigDto);
            return resut;
        });
    }

    @PostMapping("/listConfig")
    @ApiOperation(value = "配置列表")
    public CommonRestResult<Page<BaseConfigDto>> listConfig(@RequestBody PageDto pageDto) {
        return RestBusinessTemplate.execute(() -> {
            Page<BaseConfigDto> resut = baseConfigService.listConfig(pageDto);
            return resut;
        });
    }

    @PostMapping("/deleteConfig")
    @ApiOperation(value = "配置删除")
    public CommonRestResult<Boolean> deleteConfig(@RequestParam Integer id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = baseConfigService.deleteConfig(id);
            return resut;
        });
    }

    @PostMapping("/sysConfig")
    @ApiOperation(value = "在线环境输出")
    public CommonRestResult<List<Map<String, String>>> sysPrint() {
        return RestBusinessTemplate.execute(() -> {
            List<Map<String, String>> resut = baseConfigService.sysPrint();
            return resut;
        });
    }
}

