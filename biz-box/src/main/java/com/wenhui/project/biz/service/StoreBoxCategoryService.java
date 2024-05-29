package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.StoreBoxCategory;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.web.dto.BoxCategorDto;

import java.util.ArrayList;

/**
 * <p>
 * 盲盒分类表 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-06-10
 */
public interface StoreBoxCategoryService extends IService<StoreBoxCategory> {

    /**
     * 获取盲盒分类管理
     * @param cateid
     * @return
     */
    ArrayList<BoxCategorDto> queryCategory(Integer cateid);
}
