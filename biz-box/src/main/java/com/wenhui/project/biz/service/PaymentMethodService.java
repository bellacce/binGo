package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.PaymentMethod;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.web.vo.PayListPayListVo;

import java.util.List;

/**
 * <p>
 * 支付方式 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-13
 */
public interface PaymentMethodService extends IService<PaymentMethod> {

    /**
     * 获取当前开启的支付方式
     * @return
     */
    List<PayListPayListVo> queryPayList();
}
