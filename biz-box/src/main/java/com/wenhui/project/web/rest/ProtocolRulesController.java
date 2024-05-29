package com.wenhui.project.web.rest;


import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.ProtocolRulesService;
import com.wenhui.project.dal.mybatis.dataobject.ProtocolRules;
import com.wenhui.project.web.dto.ProtocolRulesDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 协议规则 前端控制器
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-19
 */
@RestController
@RequestMapping("/protocolRules")
@Api(tags = "协议规则")
public class ProtocolRulesController {

    @Resource
    private ProtocolRulesService protocolRulesService;

    @GetMapping("/query/{id}")
    @ApiOperation(value = "通过id主键获取协议内容")
    public CommonRestResult<ProtocolRulesDto> queryId(@ApiParam("协议主键。例如：1") @PathVariable Integer id) {
        return RestBusinessTemplate.execute(() -> {
            ProtocolRules protocolRules = protocolRulesService.selectById(id);
            if (protocolRules.equals(null)) {
                throw new RuntimeException("未查询到数据");
            }
            ProtocolRulesDto protocolRulesDto = new ProtocolRulesDto();
            BeanUtils.copyProperties(protocolRules, protocolRulesDto);
            return protocolRulesDto;
        });
    }

}

