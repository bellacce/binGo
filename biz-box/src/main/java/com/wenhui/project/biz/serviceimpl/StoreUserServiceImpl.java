package com.wenhui.project.biz.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.utils.*;
import com.wenhui.common.security.SecurityUtils;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.base.utils.RandomUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.base.utils.common.util.CustomStringUtils;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.integration.sms.tencent.LoginSMS;
import com.wenhui.integration.sms.tencent.TencentSmsConstant;
import com.wenhui.project.biz.config.UserShareUrlConfig;
import com.wenhui.project.biz.enums.BizConfigKeyEnum;
import com.wenhui.project.biz.service.AuthorityService;
import com.wenhui.project.biz.service.OrdersRebateService;
import com.wenhui.project.biz.service.StoreUserService;
import com.wenhui.project.dal.mybatis.dao.StoreUserMapper;
import com.wenhui.project.dal.mybatis.dataobject.Authority;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import com.wenhui.project.web.dto.StoreUserDto;
import com.wenhui.project.web.dto.UserListDto;
import com.wenhui.project.web.form.SmsLoginForm;
import com.wenhui.project.web.form.UserLoginForm;
import com.wenhui.project.web.vo.AdminAuthorityListVo;
import com.wenhui.project.web.vo.ManageListVo;
import com.wenhui.project.web.vo.SmsLoginVo;
import com.wenhui.project.web.vo.UserBoxListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreUserServiceImpl extends ServiceImpl<StoreUserMapper, StoreUser> implements StoreUserService {

    private final static Integer loginCountExpireTime = 3600;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private OrdersRebateService ordersRebateService;

    /**
     * @param phone
     * @return
     * @Override 发送验证码
     */
    public Boolean smsCode(String phone) {
        // 校验信息
        this.verifyPhoneInfo(phone);
        // 如果redis没有该手机号验证码，则获取验证码并发送短信
//        String verifyCode = RandomUtils.generateNumberRandom(6); // 获取六位验证码
        String verifyCode = "666666";
        Boolean isSend = new LoginSMS().tencentSendMessageToPhone(verifyCode, phone); // 调用tencent短信发送sdk
        // 判断发送结果并处理（存入redis）
        this.afterMessageSending(true, phone, verifyCode);
        return true;
    }

    /**
     * @param form
     * @return
     * @Override 手机登录
     */
    @Transactional(rollbackFor = Exception.class)
    public SmsLoginVo smsLogin(SmsLoginForm form, String ipAddr) {
        String verifyCode = (String) redisUtil.get(BizConfigKeyEnum.SMS_LOGIN_CODE + form.getPhones());
        if (StringUtils.isAnyBlank(verifyCode)) {
            throw new BusinessException(String.valueOf(ErrorCode.SERVER_ERROR), "请重新发送验证码");
        }
        boolean equals = verifyCode.equals(form.getSmscode());
        if (!equals) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "验证码输入错误");
        }
        StoreUser storeUser = this.userLogin(form, ipAddr);
        JwtUser userById = findUserById(storeUser.getUid() + "");
        String jwt = SecurityUtils.createJwt(604800, userById);
        SmsLoginVo smsLoginVo = new SmsLoginVo();
        smsLoginVo.setToken(jwt);
        smsLoginVo.setUserId(userById.getUserId());
        smsLoginVo.setUserName(DataDesensitizedUtils.desensitizedPhoneNumber(storeUser.getMobile()));
        smsLoginVo.setCreatedAt(storeUser.getCreatedAt());
        return smsLoginVo;
    }

    @Override
    public JwtUser findUserById(String userId) {
        StoreUser storeUser = this.selectById(userId);
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUserId((long) storeUser.getUid());
        jwtUser.setUserName(storeUser.getName());
        jwtUser.setMobile(storeUser.getMobile());
        jwtUser.setUserPassword(storeUser.getPassword());
        jwtUser.setOpenId(storeUser.getOpenid());
        return jwtUser;
    }

    @Override
    public StoreUser findStoreUserById(String userId) {
        StoreUser storeUser = this.selectById(userId);
        return storeUser;
    }

    @Override
    public String userQRcode(HttpServletRequest request, HttpServletResponse response, String userId) {
        Long uid = UserThreadLocal.get().getUserId();
        StoreUser storeUser = this.selectById(uid);
        String url2 = "";
        if (storeUser.getType()==1){
            url2 = UserShareUrlConfig.FIRST_INVITATION_LINK;
        }else{
            url2 = UserShareUrlConfig.SECOND_INVITATION_LINK;
        }
        url2 = url2 + "?uid=" + uid;
//        String url2 = request.getScheme() + "://" + request.getServerName() + "?uid=" + userId;
        String qRcodeStream = QRCodeUtil.getQRcodeStream(response, url2, null);
        return qRcodeStream;
    }

    @Override
    public SmsLoginVo adminUserLogin(UserLoginForm form) {
        StoreUser storeUser = adminLogin(form);
        JwtUser userById = findUserById(storeUser.getUid() + "");
        String jwt = SecurityUtils.createJwt(604800, userById);
        SmsLoginVo smsLoginVo = new SmsLoginVo();
        smsLoginVo.setToken(jwt);
        smsLoginVo.setUserId(userById.getUserId());
        smsLoginVo.setUserName(DataDesensitizedUtils.desensitizedPhoneNumber(storeUser.getMobile()));
        smsLoginVo.setCreatedAt(storeUser.getCreatedAt());
        return smsLoginVo;
    }

    @Override
    public List<AdminAuthorityListVo> adminAuthorityList(String manage_id) {
        StoreUser storeUser;
        if (StringUtils.isNotEmpty(manage_id)) {
            storeUser = this.baseMapper.selectById(Integer.parseInt(manage_id));
        } else {
            JwtUser jwtUser = UserThreadLocal.get();
            storeUser = this.baseMapper.selectById(jwtUser.getUserId().intValue());
        }
        List<AdminAuthorityListVo> objects = new ArrayList<>();
//        if ("admin".equals(jwtUser.getUserName())){
        if (StringUtils.isEmpty(storeUser.getUserRole())) {
            List<Authority> authorities = authorityService.selectList(new EntityWrapper<Authority>().eq("is_delete",0));
            //2、先找到一级父类，再通过级联去查询子类菜单
            List<AdminAuthorityListVo> topLevel = authorities.stream().filter(linkageEntities ->
                    "0".equals(linkageEntities.getParentId())     //查询父类
            ).map((linkageEntities) -> {
                AdminAuthorityListVo adminAuthorityListVo = new AdminAuthorityListVo();
                adminAuthorityListVo.setHas_power(true);
                adminAuthorityListVo.setId(linkageEntities.getId());
                adminAuthorityListVo.setPower_name(linkageEntities.getTitle());
                adminAuthorityListVo.setType(linkageEntities.getKey());
                adminAuthorityListVo.setSort(linkageEntities.getSort());
                adminAuthorityListVo.setUrl(linkageEntities.getUrl());
                adminAuthorityListVo.setIcon(linkageEntities.getIcon());
                adminAuthorityListVo.setChild(getChildrens(adminAuthorityListVo, authorities)); //查询子类菜单
                return adminAuthorityListVo;
            }).sorted((menu1, menu2) -> {
                //排序
                return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
            }).collect(Collectors.toList());
            return topLevel;
        } else {
            objects = JSONObject.parseArray(storeUser.getUserRole(), AdminAuthorityListVo.class);
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addManageMemberWithPower(StoreUserDto storeUserDto) {

        StoreUser storeUser;
        if (Objects.nonNull(storeUserDto.getUid())) {
            storeUser = this.selectById(storeUserDto.getUid());
        } else {
            isUser(storeUserDto.getNickname());
            if (StringUtils.isEmpty(storeUserDto.getPassword())){
                throw new BusinessException(ErrorCode.SYSTEM_EXCEPTION.getCode(), "密码不能为空");
            }
            if (!storeUserDto.getPassword().matches("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$)([^\\u4e00-\\u9fa5\\s]){6,20}$")){
                throw new BusinessException(ErrorCode.SYSTEM_EXCEPTION.getCode(), "密码格式错误，正确格式：6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种");
            }
            storeUser = new StoreUser();
        }
        BeanUtils.copyProperties(storeUserDto, storeUser, MyBeanUtils.getNullPropertyNames(storeUserDto));
        if (StringUtils.isNotEmpty(storeUserDto.getPassword())){
            storeUser.setPassword(SecurityUtils.getPassEncryption(storeUser.getPassword(), storeUser.getNickname()));
        }
        storeUser.setRecommendId(UserThreadLocal.get().getUserId().intValue());
        storeUser.setType(1);
        storeUser.setAvatarUrl("https://whshopbox-1259121814.cos.ap-nanjing.myqcloud.com//shop/image/feedback/2023-02-27/146743089229505.jpg");
        return this.insertOrUpdate(storeUser);
    }

    @Override
    public Page<ManageListVo> getManageList(PageDto pageDto) {
        int id = UserThreadLocal.get().getUserId().intValue();
        Page<ManageListVo> resultPage = new Page<>(pageDto.getCurrentPage(), pageDto.getSize());
        Page<StoreUser> storeUsers = this.selectPage(new Page<>(pageDto.getCurrentPage(), pageDto.getSize()), new EntityWrapper<StoreUser>().eq("recommend_id", id).eq("type", 1));
        List<ManageListVo> collect = storeUsers.getRecords().stream().map(u -> {
            ManageListVo manageListVo = new ManageListVo();
            manageListVo.setManage_id(u.getUid() + "");
            manageListVo.setManage_account(u.getMobile());
            manageListVo.setManage_name(u.getNickname());
            manageListVo.setQx(u.getUserRole());
            return manageListVo;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        resultPage.setTotal(storeUsers.getTotal());
        return resultPage;
    }

    @Override
    public Boolean deleteManageById(String delete_id) {
        return this.deleteById(Integer.parseInt(delete_id));
    }

    @Override
    public Page<UserBoxListVo> getUserBoxList(UserListDto pageDto) {
        Page<UserBoxListVo> resultPage = new Page<>(pageDto.getCurrentPage(), pageDto.getSize());
        Page<StoreUser> storeUsers = this.selectPage(new Page<>(pageDto.getCurrentPage(), pageDto.getSize()), new EntityWrapper<StoreUser>()
                .ne("uid", 0)
                .eq(pageDto.getIs_daRen(),"type", 1)
                .eq(StringUtils.isNotBlank(pageDto.getId()), "uid", pageDto.getId()));
        List<UserBoxListVo> collect = storeUsers.getRecords().stream().map(u -> {
            UserBoxListVo manageListVo = new UserBoxListVo();
            manageListVo.setUser_id(u.getUid() + "");
            manageListVo.setUser_icon(u.getAvatarUrl());
            manageListVo.setUser_bean(u.getBalance() + "");
            manageListVo.setUser_lucky(u.getPoint() + "");
            manageListVo.setUser_name(u.getNickname());
            if (u.getType()==1){
                manageListVo.setUser_type("达人");
            }else{
                manageListVo.setUser_type("普通用户");
            }
            BigDecimal rebate = ordersRebateService.getUserRebateById(u.getUid(), null, null);
            BigDecimal spreadRebate = ordersRebateService.getUserSpreadRebateById(u.getUid(), null, null);
            BigDecimal sum = rebate.add(spreadRebate).setScale(2, BigDecimal.ROUND_HALF_UP);
            manageListVo.setRebate(sum);
            return manageListVo;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        resultPage.setTotal(storeUsers.getTotal());
        return resultPage;
    }

    //递归查找所有菜单的子类菜单
    private static List<AdminAuthorityListVo> getChildrens(AdminAuthorityListVo root, List<Authority> all) {

        List<AdminAuthorityListVo> children = all.stream().filter(authority -> {
            return root.getId().equals(authority.getParentId());
        }).map(linkageEntities -> {
            //1、递归找到子菜单
            AdminAuthorityListVo adminAuthorityListVo = new AdminAuthorityListVo();
            adminAuthorityListVo.setHas_power(true);
            adminAuthorityListVo.setId(linkageEntities.getId());
            adminAuthorityListVo.setPower_name(linkageEntities.getTitle());
            adminAuthorityListVo.setType(linkageEntities.getKey());
            adminAuthorityListVo.setSort(linkageEntities.getSort());
            adminAuthorityListVo.setUrl(linkageEntities.getUrl());
            adminAuthorityListVo.setChild(getChildrens(adminAuthorityListVo, all));
            return adminAuthorityListVo;
        }).sorted((menu1, menu2) -> {
            //2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }


    /**
     * 校验发送验证码手机号信息
     *
     * @param phone 手机号
     */
    private void verifyPhoneInfo(String phone) {
        if (StringUtils.isAnyBlank(phone)) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "手机号为空");
        }
        // 校验手机号
        if (!CustomStringUtils.isMobile(phone)) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "手机号格式错误");
        }
        // 从redis中查看有没有该手机号的验证码
        String verifyCode = (String) redisUtil.get(BizConfigKeyEnum.SMS_LOGIN_CODE + phone);
        if (!StringUtils.isAnyBlank(verifyCode)) {
            throw new BusinessException(String.valueOf(ErrorCode.SUCCESS), "验证码已发送");
        }
        // 从redis中查看该手机号接收短信次数
        Integer count = (Integer) redisUtil.get(BizConfigKeyEnum.SMS_LOGIN_COUNT + phone);
        if (Objects.nonNull(count) && count >= 3) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "频繁登录，请稍后再试");
        }
    }

    /**
     * 判断发送结果并处理
     *
     * @param isSend     发送结果
     * @param phone      发送手机号
     * @param verifyCode 验证码
     */
    private void afterMessageSending(Boolean isSend, String phone, String verifyCode) {
        Integer expireTime = Integer.parseInt(TencentSmsConstant.EXPIRED_TIME) * 60;
        if (isSend) { // 如果发送成功，则将对应手机号验证码存入redis中，设置规定时间内有效
            redisUtil.set(
                    BizConfigKeyEnum.SMS_LOGIN_CODE + phone,
                    verifyCode,
                    expireTime);
            Integer count = (Integer) redisUtil.get(BizConfigKeyEnum.SMS_LOGIN_COUNT + phone);
            if (Objects.nonNull(count)) {
                redisUtil.update(
                        BizConfigKeyEnum.SMS_LOGIN_COUNT + phone,
                        count + 1);
            } else {
                redisUtil.set(
                        BizConfigKeyEnum.SMS_LOGIN_COUNT + phone,
                        1,
                        loginCountExpireTime);
            }

        } else {
            throw new BizException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "短信发送失败");
        }
    }

    private StoreUser userLogin(SmsLoginForm form, String ipAddr) {
        StoreUser storeUser = this.selectOne(new EntityWrapper<StoreUser>().eq("mobile", form.getPhones()));
        if (Objects.nonNull(storeUser)) {
            if (storeUser.getStatus() == 1) {
                throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "账户已被禁用！");
            }
        } else {
            storeUser = userRegister(form, ipAddr);
        }
        return storeUser;
    }

    private StoreUser adminLogin(UserLoginForm form) {
        StoreUser storeUser = this.selectOne(new EntityWrapper<StoreUser>().eq("nickname", form.getAccount()));
        if (Objects.nonNull(storeUser)) {
            if (storeUser.getStatus() == 1) {
                throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "账户已被禁用！");
            }
        } else {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "账户或密码填写错误！");
        }
        boolean judge = passwordVerify(storeUser, form.getPassword());
        if (!judge) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "账户或密码填写错误！");
        }
        return storeUser;
    }

    private StoreUser userRegister(SmsLoginForm form, String ipAddr) {
        StoreUser storeUser = new StoreUser();
        storeUser.setMobile(form.getPhones());
        storeUser.setRecommendId(StringUtils.isNoneEmpty(form.getRecommendId())?Integer.parseInt(form.getRecommendId()):null);
        storeUser.setName("临时用户");
        storeUser.setNickname(form.getPhones());
        storeUser.setPassword(UUID.randomUUID().toString());
        storeUser.setCreatedAt(new Date());
        storeUser.setRegisterIp(StringUtils.isNotEmpty(ipAddr) ? ipAddr : "");
        storeUser.setType(2);
        boolean insert = this.insert(storeUser);
        return insert ? storeUser : null;
    }
    //盐值

    private boolean passwordVerify(StoreUser storeUser, String password) {
        String passEncryption = SecurityUtils.getPassEncryption(password, storeUser.getNickname());
        return passEncryption.equals(storeUser.getPassword());
    }

    private boolean isUser(String nickname) {
        int i = this.selectCount(new EntityWrapper<StoreUser>().eq("nickname", nickname));
        if (i > 0) {
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_AUTHENTICATION_FAILED), "用户名已存在");
        }
        return true;
    }
}
