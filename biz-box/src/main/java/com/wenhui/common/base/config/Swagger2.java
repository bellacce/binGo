package com.wenhui.common.base.config;/*
 @author 天赋吉运-bms
 @DESCRIPTION Swagger2配置类
 @create 2019/8/8
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wenhui.project.web.rest"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.getParameterList());// 全局配置
    }

    /**
     * 添加head参数配置
     */
    private List<Parameter> getParameterList() {
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        clientIdTicket.name("token")
                .description("token令牌")
                .defaultValue("token-test")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("文卉网络")
                .description("路漫漫其修远兮，吾将上下而求索")
                .termsOfServiceUrl("http://www.90hou.xyz")
                .contact(new Contact("文卉科技工作室", "http://www.90hou.xyz", "WWW1437914471@163.com"))
                .version("1.0")
                .build();
    }
}
