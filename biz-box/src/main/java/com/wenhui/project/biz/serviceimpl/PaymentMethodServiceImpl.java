package com.wenhui.project.biz.serviceimpl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcloud.cos.utils.CollectionUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.project.biz.service.PaymentChannelService;
import com.wenhui.project.dal.mybatis.dataobject.PaymentChannel;
import com.wenhui.project.dal.mybatis.dataobject.PaymentMethod;
import com.wenhui.project.dal.mybatis.dao.PaymentMethodMapper;
import com.wenhui.project.biz.service.PaymentMethodService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.web.vo.PayListPayListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 支付方式 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
@Service
public class PaymentMethodServiceImpl extends ServiceImpl<PaymentMethodMapper, PaymentMethod> implements PaymentMethodService {

    @Resource
    PaymentChannelService paymentChannelService;

    /**
     * 获取当前开启的支付方式
     *
     * @return
     */
    @Override
    public List<PayListPayListVo> queryPayList() {
       /* //获取已经开启的支付渠道
        List<PaymentChannel> paymentChannels = paymentChannelService.selectList(new EntityWrapper<PaymentChannel>().eq("CHANNEL_STATE", 0));
        if(CollectionUtils.isNullOrEmpty(paymentChannels)){
            throw new BusinessException("400019","没有开启的支付渠道");
        }
        //随机取一个支付渠道渠道里面的一个集合
        int randomInt=0;
        if (paymentChannels.size()>1) {
            randomInt =RandomUtil.randomInt(0, paymentChannels.size());
        }
        PaymentChannel paymentChannel = paymentChannels.get(randomInt);*/
        //通过支付渠道去拿支付渠道下面的支付方法
//        List<PaymentMethod> paymentMethods = this.selectList(new EntityWrapper<PaymentMethod>().eq("pay_state", 0).eq("CHANNEL_ID", paymentChannel.getChannelId()));
        List<PaymentMethod> paymentMethods = this.selectList(new EntityWrapper<PaymentMethod>().eq("pay_state", 0));
        if(CollectionUtils.isNullOrEmpty(paymentMethods)){
            throw new BusinessException("400020","没有开启的支付方式");
        }
        List<PayListPayListVo> payListVos = new ArrayList<>();
        paymentMethods.stream().forEach(paymentMethod ->{
            PayListPayListVo payListPayListVo = new PayListPayListVo();
            BeanUtils.copyProperties(paymentMethod,payListPayListVo);
            payListVos.add(payListPayListVo);
        });
        return payListVos;
    }
}
