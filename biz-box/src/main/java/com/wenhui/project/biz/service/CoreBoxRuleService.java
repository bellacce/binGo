package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.CoreBoxRule;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 抽奖盒子：用户规则表 服务类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
public interface CoreBoxRuleService extends IService<CoreBoxRule> {

    CoreBoxRule getCoreBoxRule(String type, Integer proxyId);


}
