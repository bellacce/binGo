package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindboxGoods;
import com.wenhui.project.web.dto.BoxGoodsBriefDescDto;
import com.wenhui.project.web.dto.StoreBlindboxGoodsDto;

import java.util.List;

/**
 * <p>
 * 盲盒商品表 Mapper 接口
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-17
 */
public interface StoreBlindboxGoodsMapper extends BaseMapper<StoreBlindboxGoods> {


    /**
     * 通过盲盒id获取盲盒内的商品列表
     *
     * @param boxId
     * @return
     */
    List<StoreBlindboxGoodsDto> queryGoodsList(Integer boxId);

    /**
     * 根据商品id获取商品简要信息
     *
     * @param id
     * @return
     */
    BoxGoodsBriefDescDto queryGoods(Integer id);
}
