package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindboxGoods;
import com.wenhui.project.web.dto.AdminBoxGoodsAddDto;
import com.wenhui.project.web.dto.AdminBoxGoodsDetailDto;
import com.wenhui.project.web.dto.BoxGoodsBriefDescDto;
import com.wenhui.project.web.dto.StoreBlindboxGoodsDto;
import com.wenhui.project.web.vo.AdminBoxGoodsDetailVo;

import java.util.List;

/**
 * <p>
 * 盲盒商品表 服务类
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-17
 */
public interface StoreBlindboxGoodsService extends IService<StoreBlindboxGoods> {

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


    /**
     * 获取盲盒对应的商品
     * @return
     */
    Page<AdminBoxGoodsDetailVo> getBoxDetail(AdminBoxGoodsDetailDto adminBoxGoodsDetailDto);

    /**
     * 添加/修改盲盒商品
     * @return
     */
    Boolean addBoxShop(AdminBoxGoodsAddDto adminBoxGoodsDetailDto);

    /**
     * 删除盲盒商品
     * @return
     */
    Boolean deleteBoxShop(Integer boxId, Integer id);

}
