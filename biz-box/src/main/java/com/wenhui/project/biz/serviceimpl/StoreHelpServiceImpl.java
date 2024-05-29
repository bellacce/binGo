package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreHelp;
import com.wenhui.project.dal.mybatis.dao.StoreHelpMapper;
import com.wenhui.project.biz.service.StoreHelpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帮助中心 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreHelpServiceImpl extends ServiceImpl<StoreHelpMapper, StoreHelp> implements StoreHelpService {

}
