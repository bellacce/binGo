package com.wenhui.common.base.config;
/*
 @author 天赋吉运-bms
 @DESCRIPTION mybatis-plus配置文件
 @create 2019/8/9
*/

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setDialectType("mysql");
    }
}
