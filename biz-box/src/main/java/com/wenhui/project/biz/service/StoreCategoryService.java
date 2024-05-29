package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreCategory;
import com.wenhui.project.web.vo.AdminCategoryGoodsVo;
import com.wenhui.project.web.vo.AdminCategoryVo;

import java.util.List;

/**
 * <p>
 * 分类管理 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreCategoryService extends IService<StoreCategory> {

    /**
     * 获取所有分类
     * @return
     */
    List<AdminCategoryVo> addMallCategory();

    /**
     * 获取可以添加到盲盒的商品
     * @return
     */
    List<AdminCategoryGoodsVo> getBoxCanAddShopList(Integer categoryId);

    /**
     * 添加/修改分类
     * @return
     */
    Boolean updateCategory(Integer categoryId, String name);

    /**
     * 删除分类
     * @return
     */
    Boolean deleteCategory(Integer categoryId);

}
