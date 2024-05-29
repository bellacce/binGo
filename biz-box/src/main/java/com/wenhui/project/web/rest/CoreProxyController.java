package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.CoreProxyService;
import com.wenhui.project.biz.service.StoreUserService;
import com.wenhui.project.dal.mybatis.dataobject.CoreProxyDto;
import com.wenhui.project.dal.mybatis.dataobject.StoreUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 代理表 前端控制器
 * </p>
 *
 * @author XinHe
 * @since 2023-06-10
 */
@RestController
@RequestMapping("/coreProxy")
public class CoreProxyController {


    @Autowired
    private CoreProxyService coreProxyService;
    @Autowired
    private StoreUserService storeUserService;

    @GetMapping("/proxy/inComeProxy")
    @PassToken
    public CommonRestResult<Boolean> inComeProxy(@RequestParam BigDecimal price) {
        return RestBusinessTemplate.execute(() -> {
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            Boolean result = coreProxyService.inComeProxy(storeUser, price);
            return result;
        });
    }

    @GetMapping("/proxy/outComeProxy")
    @PassToken
    public CommonRestResult<Boolean> outComeProxy(@RequestParam BigDecimal price) {
        return RestBusinessTemplate.execute(() -> {
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            Boolean result = coreProxyService.outComeProxy(storeUser, price);
            return result;
        });
    }

    /**
     * todo 不对外开发
     * @param proxyId
     * @param recordId
     * @return
     */
    @GetMapping("/proxy/frozeComeProxy")
    @PassToken
    public CommonRestResult<String> frozeComeProxy(@RequestParam Integer proxyId, @RequestParam Integer recordId) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = coreProxyService.frozeComeProxy(proxyId, recordId);
            return result;
        });
    }

    @GetMapping("/proxy/getCoreProxyRecordArr")
    @PassToken
    public CommonRestResult<List<CoreProxyDto>> getCoreProxyRecordArr(@RequestParam Integer type) {
        return RestBusinessTemplate.execute(() -> {
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            List<CoreProxyDto> result = coreProxyService.getCoreProxyRecordArr(storeUser, type);
            return result;
        });
    }

    @GetMapping("/proxy/checkUserRole")
    @PassToken
    public CommonRestResult<Boolean> checkUserRole() {
        return RestBusinessTemplate.execute(() -> {
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            Boolean result = coreProxyService.checkUserRole(storeUser);
            return result;
        });
    }

}

