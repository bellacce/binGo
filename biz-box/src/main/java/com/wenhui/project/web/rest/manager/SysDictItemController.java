package com.wenhui.project.web.rest.manager;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.SysDictItemService;
import com.wenhui.project.biz.service.SysDictService;
import com.wenhui.project.dal.mybatis.dataobject.SysDictItem;
import com.wenhui.project.web.dto.GoodUpdateDto;
import com.wenhui.project.web.vo.DictItemListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典项 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/manager/sysDictItem")
@Api(tags = "字典项")
public class SysDictItemController {

    @Resource
    SysDictItemService sysDictItemService;

    @GetMapping("/list")
    @PassToken
    @ApiOperation(value = "字典项列表")
    public CommonRestResult<List<DictItemListVo>> queryDictItem(@RequestParam String dictId) {
        return RestBusinessTemplate.execute(() -> {
            System.out.println(dictId);
            List<DictItemListVo> dictItemListVos = sysDictItemService.queryDictItem(dictId);
//            Boolean resut = storeGoodsService.addShopInfo(goodUpdateDto);
            return dictItemListVos;
        });
    }

}

