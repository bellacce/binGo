package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.UserWithdrawCashList;
import com.wenhui.project.dal.mybatis.dao.UserWithdrawCashListMapper;
import com.wenhui.project.biz.service.UserWithdrawCashListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提现记录表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class UserWithdrawCashListServiceImpl extends ServiceImpl<UserWithdrawCashListMapper, UserWithdrawCashList> implements UserWithdrawCashListService {

}
