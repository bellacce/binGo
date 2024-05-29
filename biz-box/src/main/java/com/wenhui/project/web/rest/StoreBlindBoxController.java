package com.wenhui.project.web.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.*;
import com.wenhui.project.dal.mybatis.dataobject.*;
import com.wenhui.project.web.constant.CoreCusConstant;
import com.wenhui.project.web.dto.*;
import com.wenhui.project.web.vo.AdminBoxListVo;
import com.wenhui.project.web.vo.OrderDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 商品盲盒 前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/storeBlindBox")
@Api(tags = "商品盲盒")
public class StoreBlindBoxController {

    @Autowired
    private StoreBlindBoxService storeBlindBoxService;
    @Autowired
    private StoreUserService storeUserService;
    @Autowired
    private CoreProxyService coreProxyService;
    @Autowired
    private CoreBoxRuleService coreBoxRuleService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/queryBlindBoxRatio/{boxId}")
    @ApiOperation(value = "根据盲盒ID获取盲盒的概率")
    public CommonRestResult<BlindBoxRatioDto> queryBlindBoxRatio(@ApiParam(value = "盲盒id",defaultValue = "2")@PathVariable Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            BlindBoxRatioDto blindBoxRatioDto = storeBlindBoxService.queryBlindBoxRatio(boxId);
            return blindBoxRatioDto;
        });
    }




    @GetMapping("/onlineNumber")
    @ApiOperation(value = "在线人数")
    public CommonRestResult<Integer> onlineNumber() {
        return RestBusinessTemplate.execute(() -> {
            Integer min=4000,max=7000;
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            return randomNumber;
        });
    }

    @GetMapping("/unboxNumber")
    @ApiOperation(value = "已拆盲盒数量")
    public CommonRestResult<Integer> unboxNumber() {
        return RestBusinessTemplate.execute(() -> {
            Integer min=14210,max=50210;
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            return randomNumber;
        });
    }


    @GetMapping("/search/{cateId}")
    @ApiOperation(value = "首页盲盒列表（带商品）")
    public CommonRestResult<BlindBoxRatioDto> searchHomeBoxList(@ApiParam(value = "盲盒分类id",defaultValue = "1")@PathVariable Integer cateId) {
        return RestBusinessTemplate.execute(() -> {
            List<HomeBoxGoods> blindBoxRatioDto = storeBlindBoxService.searchHomeBoxList(cateId);
            return blindBoxRatioDto;
        });
    }

    @GetMapping("/query")
    @ApiOperation(value = "首页盲盒推荐列表")
    public CommonRestResult<StoreActivityDto> query() {
        return RestBusinessTemplate.execute(() -> {
            List<StoreBlindBoxDto> storeBlindBoxDtos = storeBlindBoxService.queryHomeRecommend();
            return storeBlindBoxDtos;
        });
    }

    @GetMapping("/query/{boxId}")
    @ApiOperation(value = "查询单个盲盒")
    public CommonRestResult<StoreBlindBoxSingleDto> query(@ApiParam("盲盒id") @PathVariable Integer boxId) {
        return RestBusinessTemplate.execute(() -> {
            List<StoreBlindBoxSingleDto> storeBlindBoxSingleDtos = storeBlindBoxService.queryBlindBox(boxId);
            return storeBlindBoxSingleDtos;
        });
    }

    @GetMapping("/box/accept")
    @ApiOperation(value = "收下盲盒")
    public CommonRestResult<String> accept() {
        return RestBusinessTemplate.execute(() -> {
            //获取盲盒最新的商品
            Orders orders = ordersService.selectOne(new EntityWrapper<Orders>().eq("user_id",
                    UserThreadLocal.get().getUserId()).eq("order_status", 1).orderDesc(Arrays.asList(new String[]{"created_at"})));
            Orders ordersUp = new Orders();
            //更新成已开箱
            ordersUp.setOrderStatus(2);
            ordersUp.setOrderId(orders.getOrderId());
            ordersService.updateById(ordersUp);
            return  new CommonRestResult();
        });
    }

    @GetMapping("/box/probability")
    @ApiOperation(value = "盲盒概率")
    public CommonRestResult<CoreBoxRuleDto> probability() {
        return RestBusinessTemplate.execute(() -> {
                //自然用户规则
            CoreBoxRule coreBoxRule = coreBoxRuleService.getCoreBoxRule(CoreCusConstant.RULE_NORMAL_PROXY, 0);
            CoreBoxRuleDto coreBoxRuleDto = new CoreBoxRuleDto();
            BeanUtils.copyProperties(coreBoxRule, coreBoxRuleDto);
            coreBoxRuleDto.setOneName("普通款");
            coreBoxRuleDto.setTwoName("豪华版");
            coreBoxRuleDto.setThreeName("至尊款");
            return coreBoxRuleDto;
        });
    }

    @ApiOperation(value = "开盲盒")
    @GetMapping("/open/box")
    @PassToken
    public CommonRestResult<OrderDetailVo> ordersOpenBox(@RequestParam Integer type) {
        return RestBusinessTemplate.execute(() -> {
            //校验用户是否有抽奖次数
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);
            //type 1是金币抽奖    2是次数抽奖
            if (type == 1){
                if (storeUser.getGoldAmount() < 30){
                    throw new BusinessException("100010", "金币余额不足");
                }
            } else {
                if (storeUser.getCount() <= 0){
                    throw new BusinessException("100010", "请先充值");
                }
            }

            //获取概率
            CoreBoxRule coreBoxRule;
            if(storeUser.getProxyId() != null && storeUser.getProxyId() != 0){
                CoreProxy coreProxy = coreProxyService.selectById(storeUser.getProxyId());
                if (cusId.equals(coreProxy.getCusId())){
                    //获取代理的内测规则
                    coreBoxRule = coreBoxRuleService.getCoreBoxRule(CoreCusConstant.RULE_IN_PROXY, storeUser.getProxyId());
                } else {
                   //获取代理下面的规则
                    coreBoxRule = coreBoxRuleService.getCoreBoxRule(CoreCusConstant.RULE_AND_PROXY, storeUser.getProxyId());
                }
            } else {
                //自然用户规则
                coreBoxRule = coreBoxRuleService.getCoreBoxRule(CoreCusConstant.RULE_NORMAL_PROXY, storeUser.getProxyId());
            }
            return storeBlindBoxService.ordersOpenBox(storeUser, coreBoxRule, type);
        });
    }

    @ApiOperation(value = "不想要了换金币")
    @GetMapping("/open/changeGlodCoin")
    @PassToken
    public CommonRestResult<String> changeGlodCoin(@RequestParam List<String> orderNos) {
        return RestBusinessTemplate.execute(() -> {
            String cusId = UserThreadLocal.get().getUserId()+"";
            StoreUser storeUser = storeUserService.findStoreUserById(cusId);

            List<Orders> orders = ordersService.selectList(new EntityWrapper<Orders>().eq("user_id",
                    UserThreadLocal.get().getUserId()).eq("order_status", 1)
                    .in("order_no", orderNos));

            //换金币 默认28个一枚, 默认商品换一次抽奖机会， 一个商品退回换7个金币
            StoreUser storeUserUp = new StoreUser();
            storeUserUp.setUid(storeUser.getUid());
            storeUserUp.setGoldAmount(storeUser.getGoldAmount()+orders.size()*5);
            storeUserService.updateById(storeUserUp);
            return new CommonRestResult<String>();
        });
    }
}

