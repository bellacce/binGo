package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.PaymentChannel;
import com.wenhui.project.dal.mybatis.dao.PaymentChannelMapper;
import com.wenhui.project.biz.service.PaymentChannelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付渠道  服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
@Service
public class PaymentChannelServiceImpl extends ServiceImpl<PaymentChannelMapper, PaymentChannel> implements PaymentChannelService {

}
