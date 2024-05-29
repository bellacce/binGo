package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreAddressService;
import com.wenhui.project.web.dto.UserAddressConverDto;
import com.wenhui.project.web.vo.UserAddressConverListVo;
import com.wenhui.project.web.vo.UserAddressListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户地址 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/storeAddress")
@Api(tags = "用户地址")
public class StoreAddressController {

    @Autowired
    private StoreAddressService storeAddressService;

    @GetMapping("/user/address")
    @ApiOperation(value = "用户地址列表")
    @PassToken
    public CommonRestResult<List<UserAddressListVo>> userAddressList() {
        return RestBusinessTemplate.execute(() -> {
            //此接口要加入到登录当中
            JwtUser sysUser = UserThreadLocal.get();
            List<UserAddressListVo> result = storeAddressService.userAddressList(sysUser.getUserId() + "");
            return result;
        });
    }

    @GetMapping("/select/address")
    @ApiOperation(value = "查询用户地址")
    @PassToken
    public CommonRestResult<UserAddressListVo> selectAddress(@RequestParam String id) {
        return RestBusinessTemplate.execute(() -> {
            UserAddressConverListVo result = new UserAddressConverListVo(storeAddressService.selectAddress(id));
            return result;
        });
    }

    @PostMapping("/insert/address")
    @ApiOperation(value = "新增用户地址")
    @PassToken
    public CommonRestResult<List<UserAddressListVo>> insertAddress(@Valid @RequestBody UserAddressConverDto userAddressDto) {
        return RestBusinessTemplate.execute(() -> {
            Long userId = UserThreadLocal.get().getUserId();
            userAddressDto.setUid(userId.intValue());
            Boolean result = storeAddressService.insertAddress(userAddressDto.conver());
            return result;
        });
    }

    @PostMapping("/update/address")
    @ApiOperation(value = "修改用户地址")
    @PassToken
    public CommonRestResult<Boolean> updateAddress(@Valid @RequestBody UserAddressConverDto userAddressDto) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = storeAddressService.updateAddress(userAddressDto.conver());
            return result;
        });
    }

    @GetMapping("/delete/address")
    @ApiOperation(value = "删除用户地址")
    @PassToken
    public CommonRestResult<Boolean> deleteAddress(@RequestParam String id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = storeAddressService.deleteAddress(id);
            return result;
        });
    }

}

