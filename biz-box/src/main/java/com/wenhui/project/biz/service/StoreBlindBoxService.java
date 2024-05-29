package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.project.dal.mybatis.dataobject.CoreBoxRule;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindBox;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import com.wenhui.project.web.dto.*;
import com.wenhui.project.web.vo.AdminBoxListVo;
import com.wenhui.project.web.vo.OrderDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品盲盒 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreBlindBoxService extends IService<StoreBlindBox> {

    List<StoreBlindBoxDto>  queryHomeRecommend();

    /**
     * 查询单个盲盒
     *
     * @param boxId
     * @return
     */
    List<StoreBlindBoxSingleDto> queryBlindBox(Integer boxId);
    /**
     * 查询单个盲盒概率
     *
     * @param boxId
     * @return
     */
    AdminBoxListVo probabilityBox(Integer boxId);

    /**
     * 开盲盒
     * @param storeUser
     * @param coreBoxRule
     * @return
     */
    OrderDetailVo ordersOpenBox(StoreUser storeUser, CoreBoxRule coreBoxRule, Integer type);

    /**
     * //新增/修改盲盒
     *
     * @param adminStoreBlindBoxDto
     * @return
     */
    Boolean updateBoxAllInfo(AdminStoreBlindBoxDto adminStoreBlindBoxDto);

    /**
     * //10000次开盲盒收益测试
     *
     * @return
     */
    BigDecimal testProfitBox(Integer boxId);

    /**
     * 获取盲盒列表
     *
     * @param pageDto
     * @return
     */
    Page<AdminBoxListVo> getBoxList(PageDto pageDto);
    /**
     * 设置第二套概率是否启用
     *
     * @return
     */
    Boolean changeProSettingUsed(Integer boxId, Integer type, Boolean used);
    /**
     * 删除盲盒信息
     *
     * @param boxId
     * @return
     */
    Boolean deleteBox(Integer boxId);

    /**
     * 根据盲盒id获取盲盒的概率
     * @return
     */
    BlindBoxRatioDto queryBlindBoxRatio(Integer boxId);

    /**
     * 首页盲盒列表
     * @param cateId
     * @return
     */
    List<HomeBoxGoods> searchHomeBoxList(Integer cateId);
}
