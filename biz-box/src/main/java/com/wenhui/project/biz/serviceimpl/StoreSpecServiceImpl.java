package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreSpec;
import com.wenhui.project.dal.mybatis.dao.StoreSpecMapper;
import com.wenhui.project.biz.service.StoreSpecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格组记录表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreSpecServiceImpl extends ServiceImpl<StoreSpecMapper, StoreSpec> implements StoreSpecService {

}
