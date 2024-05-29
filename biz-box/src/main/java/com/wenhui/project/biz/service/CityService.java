package com.wenhui.project.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.City;
import com.wenhui.project.web.dto.CityListDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
public interface CityService extends IService<City> {

    List<City> cityList(CityListDto cityListDto);

    Boolean provinceUpdate(JSONObject jsonObject);

    Boolean cityUpdate(JSONObject jsonObject);

    Boolean countyUpdate(JSONObject jsonObject);
}
