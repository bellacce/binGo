package com.wenhui.project.web.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.base.aop.annotation.UserLoginToken;
import com.wenhui.common.base.utils.IpUtil;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.OrdersService;
import com.wenhui.project.biz.service.StoreUserService;
import com.wenhui.project.dal.mybatis.dataobject.Orders;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import com.wenhui.project.web.dto.StoreUserInfoDto;
import com.wenhui.project.web.form.SmsLoginForm;
import com.wenhui.project.web.vo.SmsLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/storeUser")
@Api(tags = "用户中心")
public class StoreUserController {

    @Autowired
    private StoreUserService storeUserService;

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/sms/code")
    @ApiOperation(value = "发送短信验证码")
    @UserLoginToken
    public CommonRestResult<String> smsCode(@RequestParam String phones) {
        return RestBusinessTemplate.execute(() -> {
            Boolean redult = storeUserService.smsCode(phones);
            return redult ? "验证码已发送" : "验证码发送失败";
        });
    }

    @PostMapping("/sms/login")
    @ApiOperation(value = "短信验证码登录")
    @UserLoginToken
    public CommonRestResult<SmsLoginVo> smsLogin(HttpServletRequest request, @Valid @RequestBody SmsLoginForm form) {
        return RestBusinessTemplate.execute(() -> {
            String ipAddr = IpUtil.getIpAddr(request);
            SmsLoginVo smsLoginVo = storeUserService.smsLogin(form, ipAddr);
            return smsLoginVo;
        });
    }


    @GetMapping("/user/QRcode")
    @ApiOperation(value = "获取用户二维码")
    @PassToken
    public CommonRestResult userQRcode(HttpServletRequest request, HttpServletResponse response, @RequestParam String uid) {
        return RestBusinessTemplate.execute(() -> {
            String result = storeUserService.userQRcode(request, response, uid);
            return result;
        });
    }

    @GetMapping("/user/getInfo")
    @ApiOperation(value = "获取用户信息")
    @PassToken
    public CommonRestResult<StoreUserInfoDto> getInfo(HttpServletRequest request, HttpServletResponse response) {
        return RestBusinessTemplate.execute(() -> {
            StoreUserInfoDto userInfoDto = new StoreUserInfoDto();

            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            userInfoDto.setId(storeUser.getUid());
            userInfoDto.setName(storeUser.getNickname());
            userInfoDto.setPhone(storeUser.getMobile());
            userInfoDto.setAvatarUrl(storeUser.getAvatarUrl());

            List<Orders> orders = ordersService.selectList(new EntityWrapper<Orders>().eq("user_id",
                            UserThreadLocal.get().getUserId()).eq("order_status", 1)
                    .orderDesc(Arrays.asList(new String[]{"created_at"})));

            userInfoDto.setOrders(orders);
            return userInfoDto;
        });
    }

    @PostMapping("/user/changeInfo")
    @ApiOperation(value = "修改用户信息")
    @PassToken
    public CommonRestResult<String> changeInfo(@RequestBody StoreUserInfoDto userInfoDto) {
        return RestBusinessTemplate.execute(() -> {

            StoreUser user = new StoreUser();
            user.setNickname(userInfoDto.getName());
            user.setAvatarUrl(userInfoDto.getAvatarUrl());
            user.setUid(Integer.getInteger(UserThreadLocal.get().getUserId()+""));
            storeUserService.updateById(user);
            return new CommonRestResult<String>();
        });
    }
}

