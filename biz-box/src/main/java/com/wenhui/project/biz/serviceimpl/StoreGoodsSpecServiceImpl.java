package com.wenhui.project.biz.serviceimpl;

import com.wenhui.project.dal.mybatis.dataobject.StoreGoodsSpec;
import com.wenhui.project.dal.mybatis.dao.StoreGoodsSpecMapper;
import com.wenhui.project.biz.service.StoreGoodsSpecService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品与规格值关系记录表 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreGoodsSpecServiceImpl extends ServiceImpl<StoreGoodsSpecMapper, StoreGoodsSpec> implements StoreGoodsSpecService {

}
