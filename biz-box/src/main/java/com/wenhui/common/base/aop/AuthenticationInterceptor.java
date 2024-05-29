package com.wenhui.common.base.aop;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.base.aop.annotation.UserLoginToken;
import com.wenhui.common.security.SecurityUtils;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program: wh_shopbox
 * @description: jwt拦截器
 * @author: Mr.Wang
 * @create: 2023-02-12 11:08
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private StoreUserService userLoginService;

    /*
       controller执行之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        // 从http请求头中取出token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 方法是否带有UserLoginToken注释
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                return true;
            }
        }
        try {
            if (method.isAnnotationPresent(PassToken.class)) {
                PassToken passToken = method.getDeclaredAnnotation(PassToken.class);
                if (passToken.required()) {
                    // 执行认证
                    if (token == null) {
                    throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "请重新登录！");
                    }
                    // 测试环境默认token
                    if ("token-test".equals(token)) {
                        JwtUser user = userLoginService.findUserById("0");
                        UserThreadLocal.put(user);
                        return true;
                    }
                    // 获取token中的userId
                    String userId;
                    try {
                        userId = JWT.decode(token).getClaim("id").asString();
                    } catch (JWTDecodeException j) {
                    throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "访问异常，请重新登录！");
                    }
                    JwtUser user = userLoginService.findUserById(userId);
                    if (Objects.isNull(user)) {
                    throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "当前用户不存在，请重新登录！");
                    }
                    Boolean verify = SecurityUtils.isVerify(token, user);
                    if (!verify) {
                    throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()),"非法访问！");
                    }
                    UserThreadLocal.put(user);
                    return true;
                }
            }

        } catch (BusinessException ex) {
            // 方法是否带有PassToken注释
            PrintWriter writer = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "*");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                writer = response.getWriter();
//                CommonRestResult envelop = new CommonRestResult(false,ex.getCode(), ex.getMessage(),null);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code","460000");
                jsonObject.put("message","未获取到当前登录用户信息，请重新登录");
                jsonObject.put("status","false");
                writer.print(jsonObject.toJSONString());
                return false;
            }catch (Exception e) {
                System.out.println(e);
                return false;
            }finally {
                if (writer != null)
                    writer.close();
            }
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    /*
    controller执行后，页面渲染前
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /*
    页面渲染后
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
