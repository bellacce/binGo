package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreGoods;
import com.wenhui.project.web.dto.GoodsPromotionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreGoodsMapper extends BaseMapper<StoreGoods> {

    List<GoodsPromotionDto> goodsPromotion(@Param("type") Integer type);

    /**
     * 查询单个商品信息
     *
     * @param goodsId
     * @return
     */
    List<GoodsPromotionDto> productDetails(Integer goodsId);
}
