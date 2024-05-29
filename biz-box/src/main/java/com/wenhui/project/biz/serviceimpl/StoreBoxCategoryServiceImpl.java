package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreBoxCategory;
import com.wenhui.project.dal.mybatis.dao.StoreBoxCategoryMapper;
import com.wenhui.project.biz.service.StoreBoxCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.web.dto.BoxCategorDto;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 盲盒分类表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-06-10
 */
@Service
public class StoreBoxCategoryServiceImpl extends ServiceImpl<StoreBoxCategoryMapper, StoreBoxCategory> implements StoreBoxCategoryService {


    private BoxCategorDto convertToBoxCategorDto(StoreBoxCategory category) {
        BoxCategorDto boxCategorDto = new BoxCategorDto();
        boxCategorDto.setId(category.getId());
        boxCategorDto.setName(category.getName());
        boxCategorDto.setParentId(category.getParentId());
        boxCategorDto.setSort(category.getSort());
        return boxCategorDto;
    }
    /**
     * 获取盲盒分类管理
     *
     * @param cateid
     * @return
     */
    @Override
    public ArrayList<BoxCategorDto> queryCategory(Integer cateid) {
        List<StoreBoxCategory> categoryList = this.selectList(new EntityWrapper<StoreBoxCategory>().eq(!ObjectUtils.isEmpty(cateid) && cateid > 0, "id", cateid).eq("status",1).orderBy("sort"));
        ArrayList<BoxCategorDto> boxCategorDtos = categoryList.stream().map(this::convertToBoxCategorDto).collect(Collectors.toCollection(ArrayList::new));
        return boxCategorDtos;
    }
}
