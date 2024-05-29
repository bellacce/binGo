package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreBoxCategoryService;
import com.wenhui.project.web.dto.BlindBoxRatioDto;
import com.wenhui.project.web.dto.BoxCategorDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 * 盲盒分类表 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-06-10
 */
@Api(tags = "盲盒分类")
@RestController
@RequestMapping("/storeBoxCategory")
public class StoreBoxCategoryController {

    @Autowired
    private StoreBoxCategoryService storeBoxCategoryService;

    /**
     * 查询全部分类或者单独分类
     * @param cateid
     * @return
     */
    @GetMapping(value = "/search")
    public CommonRestResult<BoxCategorDto> queryCategory(@RequestParam(value = "cateid",required = false) Integer cateid){
        return RestBusinessTemplate.execute(() -> {
            ArrayList<BoxCategorDto> boxCategorDto=  storeBoxCategoryService.queryCategory(cateid);
            return boxCategorDto;
        });

    }

}

