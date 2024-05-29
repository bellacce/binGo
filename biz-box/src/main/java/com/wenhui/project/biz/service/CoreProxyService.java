package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.CoreProxy;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.CoreProxyDto;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 代理表 服务类
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
public interface CoreProxyService extends IService<CoreProxy> {

    /**
     * 代理收入增加
     * @param storeUser
     * @param price
     */
    Boolean inComeProxy(StoreUser storeUser, BigDecimal price);

    /**
     * 代理提现操作
     * @param storeUser
     * @param price
     */
    Boolean outComeProxy(StoreUser storeUser, BigDecimal price);

    /**
     * 冻结代理收入操作
     * @param proxyId
     * @param recordId
     */
    Boolean frozeComeProxy(Integer proxyId, Integer recordId);

    /**
     * 代理记录列表
     * @param storeUser
     */
    List<CoreProxyDto> getCoreProxyRecordArr(StoreUser storeUser, Integer type);

    /**
     * 校验代理角色
     * @param storeUser
     * @return
     */
    Boolean checkUserRole(StoreUser storeUser);


}
