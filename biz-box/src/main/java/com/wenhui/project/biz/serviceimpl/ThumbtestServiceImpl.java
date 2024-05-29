package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.ThumbtestService;
import com.wenhui.project.dal.mybatis.dao.ThumbtestMapper;
import com.wenhui.project.dal.mybatis.dataobject.Thumbtest;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-18
 */
@Service
public class ThumbtestServiceImpl extends ServiceImpl<ThumbtestMapper, Thumbtest> implements ThumbtestService {

}
