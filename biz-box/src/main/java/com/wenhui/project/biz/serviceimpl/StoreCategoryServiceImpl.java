package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.StoreCategoryService;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.dal.mybatis.dao.StoreCategoryMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreCategory;
import com.wenhui.project.dal.mybatis.dataobject.StoreGoods;
import com.wenhui.project.web.vo.AdminCategoryGoodsVo;
import com.wenhui.project.web.vo.AdminCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类管理 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreCategoryServiceImpl extends ServiceImpl<StoreCategoryMapper, StoreCategory> implements StoreCategoryService {

    @Autowired
    private StoreGoodsService storeGoodsService;

    @Override
    public List<AdminCategoryVo> addMallCategory() {
        List<StoreCategory> storeCategories = this.selectList(new EntityWrapper<StoreCategory>().eq("status", 0).eq("parent_id", 0));
        List<AdminCategoryVo> collect = storeCategories.stream().map(g -> {
            return new AdminCategoryVo(g.getId(), g.getName());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<AdminCategoryGoodsVo> getBoxCanAddShopList(Integer categoryId) {

        List<StoreCategory> storeCategories = this.selectList(new EntityWrapper<StoreCategory>().eq("status", 0).eq("parent_id", categoryId));
        List<Integer> collect = storeCategories.stream().map(StoreCategory::getId).collect(Collectors.toList());
        collect.add(categoryId);
        List<StoreGoods> storeGoods = storeGoodsService.selectList(new EntityWrapper<StoreGoods>().eq("status",1).eq("is_box_good",1).in("goods_type", collect));
        List<AdminCategoryGoodsVo> result = storeGoods.stream().map(g -> new AdminCategoryGoodsVo(g.getGoodsId(),g.getGoodsName(),false,g.getGoodsPrice().toString())).collect(Collectors.toList());
        return result;
    }

    @Override
    public Boolean updateCategory(Integer categoryId, String name) {
        boolean result;
        if (Objects.nonNull(categoryId)){
            result = this.updateForSet("status="+name, new EntityWrapper<StoreCategory>().eq("id", categoryId));
        }else{
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setParentId(0);
            storeCategory.setName(name);
            storeCategory.setThumb("");
            result = this.insert(storeCategory);
        }
        return result;
    }

    @Override
    public Boolean deleteCategory(Integer categoryId) {
        boolean result = this.delete(new EntityWrapper<StoreCategory>().eq("id", categoryId));
        return result;
    }
}
