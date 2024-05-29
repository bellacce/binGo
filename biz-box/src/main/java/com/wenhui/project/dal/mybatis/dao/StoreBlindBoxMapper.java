package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindBox;
import com.wenhui.project.web.dto.StoreBlindBoxDto;
import com.wenhui.project.web.dto.StoreBlindBoxSingleDto;

import java.util.List;

/**
 * <p>
 * 商品盲盒 Mapper 接口
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreBlindBoxMapper extends BaseMapper<StoreBlindBox> {

    List<StoreBlindBoxDto> queryHomeRecommend();

    /**
     * 查询单个盲盒
     *
     * @param boxId
     * @return
     */
    List<StoreBlindBoxSingleDto> queryBlindBox(Integer boxId);
}
