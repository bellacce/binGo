package com.wenhui.project.biz.config;/*
 @author 天赋吉运-bms
 @DESCRIPTION 配置类启动加载
 @create 2023/3/19
*/

import com.wenhui.project.biz.service.BaseConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 回调执行Bean.
 *
 * @author xindaqi
 * @since 2022-11-19 21:59
 */
@Component
public class BaseConfigRunner  implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(BaseConfigRunner.class);

    @Resource
    private BaseConfigService baseConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //腾讯sms
        baseConfigService.updateConfigToActivate(3);
        //腾讯cos
        baseConfigService.updateConfigToActivate(4);
        //收益划分
        baseConfigService.updateConfigToActivate(5);
        //域名链接配置
        baseConfigService.updateConfigToActivate(6);

    }
}
