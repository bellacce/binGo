package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.CorePayRecord;
import com.wenhui.project.dal.mybatis.dao.CorePayRecordMapper;
import com.wenhui.project.biz.service.CorePayRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 客户支付记录&代理提现记录 服务实现类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-18
 */
@Service
public class CorePayRecordServiceImpl extends ServiceImpl<CorePayRecordMapper, CorePayRecord> implements CorePayRecordService {

    @Override
    public void save(Integer type, String mobile, String name, Integer id, BigDecimal price) {
        CorePayRecord record = new CorePayRecord();
        record.setAmount(price);
        record.setMobile(mobile);
        record.setName(name);
        record.setRelateId(id);
        record.setType(type);
        record.setCreatedAt(new Date());
        this.baseMapper.insert(record);
    }
}
