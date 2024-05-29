package com.wenhui.common.base.config;

import com.wenhui.common.base.aop.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: wh_shopbox
 * @description: jwt 后台访问拦截
 * @author: Mr.Wang
 * @create: 2023-02-12 11:07
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(authenticationInterceptor());
        registration.addPathPatterns("/**");//所有路径都被拦截
        registration.excludePathPatterns("/storeUser/sms/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept,Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "token")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}