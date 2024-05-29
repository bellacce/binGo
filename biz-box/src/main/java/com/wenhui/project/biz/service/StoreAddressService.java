package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreAddress;
import com.wenhui.project.web.dto.UserAddressDto;
import com.wenhui.project.web.vo.UserAddressListVo;

import java.util.List;

/**
 * <p>
 * 用户地址 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreAddressService extends IService<StoreAddress> {
    List<UserAddressListVo> userAddressList(String uid);

    UserAddressListVo selectAddress(String id);

    Boolean insertAddress(UserAddressDto userAddressDto);

    Boolean updateAddress(UserAddressDto userAddressDto);

    Boolean deleteAddress(String id);

    /**
     * 获取用户默认地址
     *
     * @param uid
     * @return
     */
    UserAddressListVo defaultAddress(String uid);
}
