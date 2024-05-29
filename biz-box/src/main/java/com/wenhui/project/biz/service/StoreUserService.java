package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import com.wenhui.project.web.dto.StoreUserDto;
import com.wenhui.project.web.dto.UserListDto;
import com.wenhui.project.web.form.SmsLoginForm;
import com.wenhui.project.web.form.UserLoginForm;
import com.wenhui.project.web.vo.AdminAuthorityListVo;
import com.wenhui.project.web.vo.ManageListVo;
import com.wenhui.project.web.vo.SmsLoginVo;
import com.wenhui.project.web.vo.UserBoxListVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreUserService extends IService<StoreUser> {

    /**
     * @param phone
     * @return
     * @Override 发送验证码
     */
    Boolean smsCode(String phone);

    /**
     * @param form
     * @return
     * @Override 手机登录
     */
    SmsLoginVo smsLogin(SmsLoginForm form, String ipAddr);

    JwtUser findUserById(String userId);

    StoreUser findStoreUserById(String userId);

    String userQRcode(HttpServletRequest request, HttpServletResponse response, String userId);

    /**
     * @param form
     * @return
     * @Override 用户登录
     */
    SmsLoginVo adminUserLogin(UserLoginForm form);

    /**
     * 获取指定用户的权限
     *
     * @return
     */
    List<AdminAuthorityListVo> adminAuthorityList(String manage_id);

    /**
     * 添加管理员
     *
     * @param storeUserDto
     * @return
     */
    Boolean addManageMemberWithPower(StoreUserDto storeUserDto);

    /**
     * 获取管理员列表
     *
     * @return
     */
    Page<ManageListVo> getManageList(PageDto pageDto);

    /**
     * 管理员删除
     *
     * @param manage_id
     * @return
     */
    Boolean deleteManageById(String delete_id);

    /**
     * 获取h5用户列表
     *
     * @return
     */
    Page<UserBoxListVo> getUserBoxList(UserListDto userListDto);

}
