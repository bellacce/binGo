package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.SysDict;
import com.wenhui.project.dal.mybatis.dao.SysDictMapper;
import com.wenhui.project.biz.service.SysDictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
