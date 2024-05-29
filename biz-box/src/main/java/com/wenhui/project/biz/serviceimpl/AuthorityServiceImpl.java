package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.AuthorityService;
import com.wenhui.project.dal.mybatis.dao.AuthorityMapper;
import com.wenhui.project.dal.mybatis.dataobject.Authority;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限菜单表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-04
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}
