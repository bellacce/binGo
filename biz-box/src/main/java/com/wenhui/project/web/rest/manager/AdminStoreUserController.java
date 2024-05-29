package com.wenhui.project.web.rest.manager;


import com.baomidou.mybatisplus.plugins.Page;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.base.aop.annotation.UserLoginToken;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreUserService;
import com.wenhui.project.web.dto.StoreUserDto;
import com.wenhui.project.web.dto.UserListDto;
import com.wenhui.project.web.form.UserLoginForm;
import com.wenhui.project.web.vo.AdminAuthorityListVo;
import com.wenhui.project.web.vo.ManageListVo;
import com.wenhui.project.web.vo.SmsLoginVo;
import com.wenhui.project.web.vo.UserBoxListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/manager/storeUser")
@Api(tags = "后台用户中心")
public class AdminStoreUserController {

    @Autowired
    private StoreUserService storeUserService;

    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录")
    @UserLoginToken
    public CommonRestResult<SmsLoginVo> adminUserLogin(@Valid @RequestBody UserLoginForm form) {
        return RestBusinessTemplate.execute(() -> {
            SmsLoginVo smsLoginVo = storeUserService.adminUserLogin(form);
            return smsLoginVo;
        });
    }

    @PostMapping("/user/authority")
    @ApiOperation(value = "获取指定用户的权限")
    @PassToken
    public CommonRestResult<List<AdminAuthorityListVo>> adminAuthorityList(@RequestParam(required = false) String query_id) {
        return RestBusinessTemplate.execute(() -> {
            List<AdminAuthorityListVo> adminAuthorityListVo = storeUserService.adminAuthorityList(query_id);
            return adminAuthorityListVo;
        });
    }

    @PostMapping("/user/add/power")
    @ApiOperation(value = "添加修改管理员")
    @PassToken
    public CommonRestResult<Boolean> addManageMemberWithPower(@RequestBody @Valid StoreUserDto storeUserDto) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = storeUserService.addManageMemberWithPower(storeUserDto);
            return result;
        });
    }

    @PostMapping("/user/list/power")
    @ApiOperation(value = "获取管理员列表")
    @PassToken
    public CommonRestResult<Page<ManageListVo>> getManageList(@RequestBody PageDto pageDto) {
        return RestBusinessTemplate.execute(() -> {
            Page<ManageListVo> result = storeUserService.getManageList(pageDto);
            return result;
        });
    }

    @PostMapping("/user/delete/power")
    @ApiOperation(value = "管理员删除")
    @PassToken
    public CommonRestResult<Page<ManageListVo>> deleteManageById(@RequestParam String delete_id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = storeUserService.deleteManageById(delete_id);
            return result;
        });
    }


    @PostMapping("/user/list/box")
    @ApiOperation(value = "获取h5用户列表")
    @PassToken
    public CommonRestResult<Page<UserBoxListVo>> getUserBoxList(@RequestBody UserListDto userListDto) {
        return RestBusinessTemplate.execute(() -> {
            Page<UserBoxListVo> result = storeUserService.getUserBoxList(userListDto);
            return result;
        });
    }
}

