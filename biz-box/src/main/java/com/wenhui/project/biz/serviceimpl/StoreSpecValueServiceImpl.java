package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreSpecValue;
import com.wenhui.project.dal.mybatis.dao.StoreSpecValueMapper;
import com.wenhui.project.biz.service.StoreSpecValueService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格值记录表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreSpecValueServiceImpl extends ServiceImpl<StoreSpecValueMapper, StoreSpecValue> implements StoreSpecValueService {

}
