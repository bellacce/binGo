package com.wenhui.project.biz.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.project.biz.service.CityService;
import com.wenhui.project.biz.service.StoreAddressService;
import com.wenhui.project.dal.mybatis.dao.StoreAddressMapper;
import com.wenhui.project.dal.mybatis.dataobject.City;
import com.wenhui.project.dal.mybatis.dataobject.StoreAddress;
import com.wenhui.project.web.dto.UserAddressDto;
import com.wenhui.project.web.vo.UserAddressListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户地址 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreAddressServiceImpl extends ServiceImpl<StoreAddressMapper, StoreAddress> implements StoreAddressService {

    @Autowired
    private CityService cityService;

    @Override
    public List<UserAddressListVo> userAddressList(String uid) {
        List<StoreAddress> addresses = this.selectList(new EntityWrapper<StoreAddress>().eq("uid", uid).orderDesc(Arrays.asList(new String[]{"created_at"})));
        ArrayList<UserAddressListVo> results = new ArrayList<>();
        addresses.forEach(a -> {
            UserAddressListVo userAddressListVo = new UserAddressListVo();
            BeanUtils.copyProperties(a, userAddressListVo);
            userAddressListVo.setProvinceName(cityService.selectOne(new EntityWrapper<City>().eq("code", a.getProvince())).getName());
            userAddressListVo.setCityName(cityService.selectOne(new EntityWrapper<City>().eq("code", a.getCity())).getName());
            userAddressListVo.setRegionName(cityService.selectOne(new EntityWrapper<City>().eq("code", a.getRegion())).getName());
            results.add(userAddressListVo);
        });
        return results;
    }

    @Override
    public UserAddressListVo selectAddress(String id) {
        StoreAddress storeAddress = this.selectById(id);
        if (storeAddress.equals(null)) {
            throw new BusinessException(String.valueOf(ErrorCode.DATA_EMPTY_DATA), "未查询到相关数据");
        }
        UserAddressListVo userAddressListVo = new UserAddressListVo();
        BeanUtils.copyProperties(storeAddress, userAddressListVo);
        userAddressListVo.setProvinceName(cityService.selectOne(new EntityWrapper<City>().eq("code", userAddressListVo.getProvince())).getName());
        userAddressListVo.setCityName(cityService.selectOne(new EntityWrapper<City>().eq("code", userAddressListVo.getCity())).getName());
        userAddressListVo.setRegionName(cityService.selectOne(new EntityWrapper<City>().eq("code", userAddressListVo.getRegion())).getName());
        return userAddressListVo;
    }

    @Override
    public Boolean insertAddress(UserAddressDto userAddressDto) {
        City code = cityService.selectOne(new EntityWrapper<City>().eq("code", userAddressDto.getRegion()));
        if (Objects.isNull(code)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST.getCode(), "此地址不支持，请联系管理员操作。");
        }
        StoreAddress storeAddress = new StoreAddress();
        BeanUtils.copyProperties(userAddressDto, storeAddress);
        storeAddress.setCreatedAt(new Date());
        boolean insert = this.insert(storeAddress);
        return insert;
    }

    @Override
    public Boolean updateAddress(UserAddressDto userAddressDto) {
        City code = cityService.selectOne(new EntityWrapper<City>().eq("code", userAddressDto.getRegion()));
        if (Objects.isNull(code)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST.getCode(), "此地址不支持，请联系管理员操作。");
        }
        StoreAddress storeAddress = new StoreAddress();
        BeanUtils.copyProperties(userAddressDto, storeAddress);
        storeAddress.setUpdatedAt(new Date());
        boolean update = this.updateById(storeAddress);
        return update;
    }

    @Override
    public Boolean deleteAddress(String id) {
        return this.deleteById(Integer.parseInt(id));
    }

    /**
     * 获取用户默认地址
     *
     * @param uid
     * @return
     */
    @Override
    public UserAddressListVo defaultAddress(String uid) {
        StoreAddress addresses = this.selectOne(new EntityWrapper<StoreAddress>().eq("uid", uid).eq("delete_flag", 0).orderDesc(Arrays.asList(new String[]{"is_default"})));
        if (BeanUtil.isEmpty(addresses)) {
            addresses = this.selectOne(new EntityWrapper<StoreAddress>().eq("uid", uid).eq("delete_flag", 0).orderDesc(Arrays.asList(new String[]{"created_at"})));
        }
        UserAddressListVo userAddressListVo = new UserAddressListVo();
        if (!BeanUtil.isEmpty(addresses)) {
            userAddressListVo.setProvinceName(cityService.selectOne(new EntityWrapper<City>().eq("code", addresses.getProvince())).getName());
            userAddressListVo.setCityName(cityService.selectOne(new EntityWrapper<City>().eq("code", addresses.getCity())).getName());
            userAddressListVo.setRegionName(cityService.selectOne(new EntityWrapper<City>().eq("code", addresses.getRegion())).getName());
            BeanUtils.copyProperties(addresses, userAddressListVo);
        }
        return userAddressListVo;
    }


}
