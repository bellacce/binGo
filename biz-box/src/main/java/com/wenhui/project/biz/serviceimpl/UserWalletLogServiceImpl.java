package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.UserWalletLog;
import com.wenhui.project.dal.mybatis.dao.UserWalletLogMapper;
import com.wenhui.project.biz.service.UserWalletLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户钱包流水记录表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class UserWalletLogServiceImpl extends ServiceImpl<UserWalletLogMapper, UserWalletLog> implements UserWalletLogService {

}
