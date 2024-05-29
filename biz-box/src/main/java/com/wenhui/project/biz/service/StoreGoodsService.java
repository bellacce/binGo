package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreGoods;
import com.wenhui.project.web.dto.GoodUpdateDto;
import com.wenhui.project.web.dto.GoodsListDto;
import com.wenhui.project.web.dto.GoodsPromotionDto;
import com.wenhui.project.web.vo.GoodsListVo;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreGoodsService extends IService<StoreGoods> {

    /**
     * 商品活动专区列表查询
     *
     * @param type 1：只查询前20条
     * @return
     */
    List<GoodsPromotionDto> goodsPromotion(Integer type);

    /**
     * 查询单个商品信息
     *
     * @param goodsId
     * @return
     */
    List<GoodsPromotionDto> productDetails(Integer goodsId);


    /**
     * 获取商品列表
     *
     * @param goodsListDto
     * @return
     */
    Page<GoodsListVo> getShopList(GoodsListDto goodsListDto);

    /**
     * 新增商品
     *
     * @param goodsListDto
     * @return
     */
    Boolean addShopInfo(GoodUpdateDto goodsListDto);

    /**
     * 新增商品
     *
     * @return
     */
    Boolean changeShopStatus(String id, Boolean status);
    /**
     * 删除商品
     *
     * @return
     */
    Boolean deleteShopList(String id);
}
