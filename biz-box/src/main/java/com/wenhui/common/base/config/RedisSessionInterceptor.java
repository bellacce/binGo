package com.wenhui.common.base.config;
/*
 @author 天赋吉运-bms
 @DESCRIPTION 登录状态拦截器
 @create 2019/8/13
*/

import com.alibaba.fastjson.JSON;
import com.wenhui.common.base.utils.RedisUtil;
import com.wenhui.dal.ReturnData;
import com.wenhui.dal.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//拦截登录失效的请求
public class RedisSessionInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    Logger log = LoggerFactory.getLogger(RedisSessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
        System.out.println("添加跨域支持");
        //添加跨域CORS
        /*response.addHeader("Access-Control-Max-Age", "1800");//30 min
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Content-Type,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
       */
        String token = ""/*CookieUtils.getCookieValue(request, "custom.name")*/;
        log.info("传递到的cookie：" + token);
        if (!StringUtils.isEmpty(token)) {
           /* String loginCode = (String) redisUtil.get(token);
            System.out.println(loginCode);
            if (!StringUtils.isEmpty(loginCode)) {
                return false;
            } else {
                response401(response);
                return false;
            }*/
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            response.getWriter().print(JSON.toJSONString(new ReturnData(StatusCode.NEED_LOGIN, "", "用户未登录！")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
