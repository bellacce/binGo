package com.wenhui.project.web.rest;


import com.alibaba.fastjson.JSONObject;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.CityService;
import com.wenhui.project.dal.mybatis.dataobject.City;
import com.wenhui.project.web.dto.CityListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
@RestController
@RequestMapping("/city")
@Api(tags = "城市列表")
public class CityController {

    @Autowired
    private CityService cityService;


    @PostMapping("/city/list")
    @ApiOperation(value = "城市子集列表查询，查询省级列表只传type为1，查询市级和区级传入选择的上级地址的id和type")
    @PassToken
    public CommonRestResult<List<City>> cityList(@Valid @RequestBody CityListDto cityListDto) {
        return RestBusinessTemplate.execute(() -> {
            List<City> result = cityService.cityList(cityListDto);
            return result;
        });
    }

    @PostMapping("/province/update")
    @ApiOperation(value = "省级区划更新 数据地址：https://github.com/youzan/vant/blob/main/packages/vant-area-data/src/index.ts")
    @PassToken
    public CommonRestResult<Boolean> provinceUpdate(@RequestBody Map data) {
        return RestBusinessTemplate.execute(() -> {
            JSONObject jsonObject = JSONObject.parseObject((String) data.get("data"));
            Boolean result = cityService.provinceUpdate(jsonObject);
            return result;
        });
    }

    @PostMapping("/city/update")
    @ApiOperation(value = "市级区划更新 数据地址：https://github.com/youzan/vant/blob/main/packages/vant-area-data/src/index.ts")
    @PassToken
    public CommonRestResult<Boolean> cityUpdate(@RequestBody Map data) {
        return RestBusinessTemplate.execute(() -> {
            JSONObject jsonObject = JSONObject.parseObject((String) data.get("data"));
            Boolean result = cityService.cityUpdate(jsonObject);
            return result;
        });
    }

    @PostMapping("/county/update")
    @ApiOperation(value = "区级区划更新 数据地址：https://github.com/youzan/vant/blob/main/packages/vant-area-data/src/index.ts")
    @PassToken
    public CommonRestResult<Boolean> countyUpdate(@RequestBody Map data) {
        return RestBusinessTemplate.execute(() -> {
            JSONObject jsonObject = JSONObject.parseObject((String) data.get("data"));
            Boolean result = cityService.countyUpdate(jsonObject);
            return result;
        });
    }

}

