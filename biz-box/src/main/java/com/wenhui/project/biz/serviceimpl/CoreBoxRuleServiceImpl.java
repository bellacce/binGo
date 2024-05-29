package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.project.biz.service.CoreBoxProxyRuleService;
import com.wenhui.project.dal.mybatis.dataobject.Authority;
import com.wenhui.project.dal.mybatis.dataobject.CoreBoxProxyRule;
import com.wenhui.project.dal.mybatis.dataobject.CoreBoxRule;
import com.wenhui.project.dal.mybatis.dao.CoreBoxRuleMapper;
import com.wenhui.project.biz.service.CoreBoxRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.web.constant.CoreCusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 抽奖盒子：用户规则表 服务实现类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@Service
public class CoreBoxRuleServiceImpl extends ServiceImpl<CoreBoxRuleMapper, CoreBoxRule> implements CoreBoxRuleService {

    @Autowired
    private CoreBoxProxyRuleService coreBoxProxyRuleService;

    @Override
    public CoreBoxRule getCoreBoxRule(String type, Integer proxyId) {
        CoreBoxRule coreBoxRule = new CoreBoxRule();
        if (CoreCusConstant.RULE_IN_PROXY.equals(type)){
            //coreBoxProxyRuleService;
            EntityWrapper<CoreBoxProxyRule> wrapper = new EntityWrapper<>();
            wrapper.eq("proxy_id", proxyId);
            CoreBoxProxyRule coreBoxProxyRule = (CoreBoxProxyRule) coreBoxProxyRuleService.selectOne(wrapper);
            BeanUtils.copyProperties(coreBoxProxyRule, coreBoxRule);
        }
        if (CoreCusConstant.RULE_AND_PROXY.equals(type)){
            EntityWrapper<CoreBoxRule> wrapper = new EntityWrapper<>();
            wrapper.eq("proxy_id", proxyId);
            coreBoxRule = (CoreBoxRule) this.selectOne(wrapper);
        }
        if (CoreCusConstant.RULE_NORMAL_PROXY.equals(type)){
            EntityWrapper<CoreBoxRule> wrapper = new EntityWrapper<>();
            wrapper.eq("proxy_id", 0);
            coreBoxRule = (CoreBoxRule) this.selectOne(wrapper);

        }
        return coreBoxRule;
    }
}
