package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.UserWallet;
import com.wenhui.project.dal.mybatis.dao.UserWalletMapper;
import com.wenhui.project.biz.service.UserWalletService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户钱包 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

}
