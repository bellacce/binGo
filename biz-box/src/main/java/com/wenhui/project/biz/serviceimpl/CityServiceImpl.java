package com.wenhui.project.biz.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.CityService;
import com.wenhui.project.dal.mybatis.dao.CityMapper;
import com.wenhui.project.dal.mybatis.dataobject.City;
import com.wenhui.project.web.dto.CityListDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

    @Override
    public List<City> cityList(CityListDto cityListDto) {
        List<City> type;
        if (Objects.nonNull(cityListDto.getId())) {
            type = this.selectList(new EntityWrapper<City>().eq("type", "1").orderAsc(Arrays.asList(new String[]{"code"})));
        } else {
            type = this.selectList(new EntityWrapper<City>().eq("pid", cityListDto.getId()).eq("type", String.valueOf(Integer.parseInt(cityListDto.getType()) + 1)).orderAsc(Arrays.asList(new String[]{"code"})));
        }
        return type;
    }


    //地市区划更新需要分别请求省 市  区 更新接口，数据从git地址中获取，分别拷贝省市区的json数据，先压缩，再以map {"data":""}的形式传入
    @Override
    public Boolean provinceUpdate(JSONObject obj) {
        for (String str : obj.keySet()) {
            Integer areacode = Integer.parseInt(str);
            String name = (String) obj.get(str);
            City code = this.selectOne(new EntityWrapper<City>().eq("code", areacode));
            if (code.getName().equals(name)) {
                code.setName(name);
                this.updateById(code);
            } else {
                City city = new City();
                city.setType("1");
                city.setCode(areacode);
                city.setName(name);
                city.setPid(10000000);
                this.insert(city);
            }
        }
        return true;
    }

    @Override
    public Boolean cityUpdate(JSONObject obj) {
        for (String str : obj.keySet()) {
            Integer areacode = Integer.parseInt(str);
            String name = (String) obj.get(str);
            City code = this.selectOne(new EntityWrapper<City>().eq("code", areacode));
            if (code.getName().equals(name)) {
                code.setName(name);
                boolean update = this.updateById(code);
            } else {
                City code1 = this.selectOne(new EntityWrapper<City>().eq("code", String.valueOf(areacode).substring(0, 2) + "0000"));
                if (Objects.nonNull(code1)) {
                    City city = new City();
                    city.setType("2");
                    city.setCode(areacode);
                    city.setName(name);
                    city.setPid(code1.getId());
                    this.insert(city);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean countyUpdate(JSONObject obj) {
        for (String str : obj.keySet()) {
            Integer areacode = Integer.parseInt(str);
            String name = (String) obj.get(str);
            City code = this.selectOne(new EntityWrapper<City>().eq("code", areacode));
            if (Objects.nonNull(code) && code.getName().equals(name)) {
                code.setName(name);
                boolean update = this.updateById(code);
            } else {
                City code1 = this.selectOne(new EntityWrapper<City>().eq("code", String.valueOf(areacode).substring(0, 4) + "00"));
                if (Objects.nonNull(code1)) {
                    City city = new City();
                    city.setType("3");
                    city.setCode(areacode);
                    city.setName(name);
                    city.setPid(code1.getId());
                    this.insert(city);
                }
            }
        }
        return true;
    }
}
