package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.CorePayRecord;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 客户支付记录&代理提现记录 服务类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-18
 */
public interface CorePayRecordService extends IService<CorePayRecord> {

    void save(Integer type, String mobile, String name, Integer id, BigDecimal price);

}
