package com.wenhui.project.web.rest.manager;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreCategoryService;
import com.wenhui.project.web.vo.AdminCategoryGoodsVo;
import com.wenhui.project.web.vo.AdminCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分类管理 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/manager/storeCategory")
@Api(tags = "后台商品分类")
public class AdminStoreCategoryController {

    @Autowired
    private StoreCategoryService storeCategoryService;

    @PostMapping("/category/list")
    @ApiOperation(value = "获取所有分类")
    public CommonRestResult<AdminCategoryVo> addMallCategory() {
        return RestBusinessTemplate.execute(() -> {
            List<AdminCategoryVo> resut = storeCategoryService.addMallCategory();
            return resut;
        });
    }

    @PostMapping("/category/list/goods")
    @ApiOperation(value = "获取可以添加到盲盒的商品")
    public CommonRestResult<AdminCategoryGoodsVo> getBoxCanAddShopList(@RequestParam Integer categoryId ) {
        return RestBusinessTemplate.execute(() -> {
            List<AdminCategoryGoodsVo> resut = storeCategoryService.getBoxCanAddShopList(categoryId);
            return resut;
        });
    }

    @PostMapping("/category/add")
    @ApiOperation(value = "添加/修改分类")
    public CommonRestResult<Boolean> updateCategory(@RequestParam(required = false) Integer id, @RequestParam String name) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeCategoryService.updateCategory(id, name);
            return resut;
        });
    }

    @PostMapping("/category/delete")
    @ApiOperation(value = "删除分类")
    public CommonRestResult<Boolean> deleteCategory(@RequestParam Integer id) {
        return RestBusinessTemplate.execute(() -> {
            Boolean resut = storeCategoryService.deleteCategory(id);
            return resut;
        });
    }
}

