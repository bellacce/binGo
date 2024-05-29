package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.project.biz.constant.CommonConstant;
import com.wenhui.project.biz.service.CorePayRecordService;
import com.wenhui.project.dal.mybatis.dataobject.CorePayRecord;
import com.wenhui.project.dal.mybatis.dataobject.CoreProxy;
import com.wenhui.project.dal.mybatis.dao.CoreProxyMapper;
import com.wenhui.project.biz.service.CoreProxyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.dal.mybatis.dataobject.CoreProxyDto;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代理表 服务实现类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@Service
public class CoreProxyServiceImpl extends ServiceImpl<CoreProxyMapper, CoreProxy> implements CoreProxyService {

    private CorePayRecordService corePayRecordService;

    /**
     * 总收益  可提现 提现中 已提现
     * 代理收入增加
     * @param storeUser
     * @param price
     */
    @Override
    public Boolean inComeProxy(StoreUser storeUser, BigDecimal price){
        if (storeUser.getProxyId() == 0 || storeUser.getProxyId() == null){
            return false;
        }
        //查询代理信息
        CoreProxy coreProxy = this.baseMapper.selectById(storeUser.getProxyId());

        //提成比例*price
        BigDecimal inCome = coreProxy.getExtensionScale().multiply(price).divide(new BigDecimal(100));

        //账户余额累计
        BigDecimal addCome = coreProxy.getExtensionAmount().add(inCome);

        CoreProxy coreProxyUp = new CoreProxy();
        coreProxyUp.setId(storeUser.getProxyId());
        //更新收入
        coreProxyUp.setExtensionAmount(addCome);
        int flag = this.baseMapper.updateById(coreProxyUp);
        if (flag > 0){
            //添加记录
            corePayRecordService.save(CommonConstant.PAY_RECORD_PROXY_INCOME, storeUser.getMobile(),
                    storeUser.getName(), storeUser.getUid(), price);
            return true;
        }
        return false;
    }

    /**
     * 代理提现
     * @param storeUser
     * @param price
     */
    @Override
    public Boolean outComeProxy(StoreUser storeUser, BigDecimal price) {
        if (storeUser.getProxyId() == 0 || storeUser.getProxyId() == null){
            return false;
        }
        //查询代理信息
        CoreProxy coreProxy = this.baseMapper.selectById(storeUser.getProxyId());
        //可金额校验
        BigDecimal useCash = coreProxy.getExtensionAmount().subtract(coreProxy.getExtensionFreeze())
                .subtract(coreProxy.getExtensionFreeze())
                .subtract(coreProxy.getExtensionWithdrawing());
        //提现金额
        if (price.compareTo(useCash) == -1){
            return false;
        }
        //增加提现中金额
        BigDecimal addComeDraw = coreProxy.getExtensionWithdrawing().add(price);

        CoreProxy coreProxyUp = new CoreProxy();
        coreProxyUp.setId(coreProxy.getId());
        coreProxyUp.setExtensionWithdrawing(addComeDraw);
        int flag = this.baseMapper.updateById(coreProxyUp);
        if (flag > 0){
            //添加记录
            corePayRecordService.save(CommonConstant.PAY_RECORD_PROXY_OUTCOME, coreProxy.getMobile(),
                    coreProxy.getName(), coreProxy.getId(), price);
            return true;
        }
        return false;
    }

    @Override
    public Boolean frozeComeProxy(Integer proxyId, Integer recordId) {
        CoreProxy coreProxy = this.baseMapper.selectById(proxyId);
        CorePayRecord corePayRecord = corePayRecordService.selectById(recordId);

        CoreProxy coreProxyUp = new CoreProxy();
        coreProxyUp.setId(proxyId);
        coreProxyUp.setExtensionFreeze(coreProxy.getExtensionFreeze().add(corePayRecord.getAmount()));
        coreProxyUp.setExtensionAmount(coreProxy.getExtensionAmount().subtract(corePayRecord.getAmount()));
        Integer flag = this.baseMapper.updateById(coreProxyUp);
        if (flag > 0){
            //添加记录
            corePayRecordService.save(CommonConstant.PAY_RECORD_PROXY_FROZE, coreProxy.getMobile(),
                    coreProxy.getName(), coreProxy.getId(), corePayRecord.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public List<CoreProxyDto> getCoreProxyRecordArr(StoreUser storeUser, Integer type) {
        if (storeUser.getProxyId() == 0 || storeUser.getProxyId() == null){
            return new ArrayList<>();
        }
        CoreProxy coreProxy = this.baseMapper.selectById(storeUser.getProxyId());
        //校验该用户是否是代理
        if (!coreProxy.getCusId().equals(storeUser.getUid())){
            return new ArrayList<>();
        }
//        new EntityWrapper<MfImage>().eq("is_delete", 0)
        List<CorePayRecord> corePayRecords = corePayRecordService.selectList(new EntityWrapper<CorePayRecord>().eq("role_type",
                type).eq("relate_id", coreProxy.getId()));

        List<CoreProxyDto> coreProxyDtos = new ArrayList<>();
        corePayRecords.stream().forEach(o->{
            CoreProxyDto dto = new CoreProxyDto();
            BeanUtils.copyProperties(o,dto);
            coreProxyDtos.add(dto);
        });
        return coreProxyDtos;
    }

    /**
     * 校验是否用户是否是代理
     * @param storeUser
     * @return
     */
    public Boolean checkUserRole(StoreUser storeUser) {
        if (storeUser.getProxyId() == 0 || storeUser.getProxyId() == null){
            return false;
        }
        CoreProxy coreProxy = this.baseMapper.selectById(storeUser.getProxyId());
        //校验该用户是否是代理
        if (!coreProxy.getCusId().equals(storeUser.getUid())){
            return false;
        }
        return true;
    }

}
