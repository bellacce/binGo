package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreAdvert;
import com.wenhui.project.dal.mybatis.dao.StoreAdvertMapper;
import com.wenhui.project.biz.service.StoreAdvertService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告管理 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreAdvertServiceImpl extends ServiceImpl<StoreAdvertMapper, StoreAdvert> implements StoreAdvertService {

}
