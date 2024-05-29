package com.wenhui.project.biz.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.enums.OpenBoxEnum;
import com.wenhui.common.base.utils.MyBeanUtils;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.core.base.utils.RandomUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.integration.pay.alipay.AlipayServiceCallConstant;
import com.wenhui.project.biz.service.*;
import com.wenhui.project.biz.util.*;
import com.wenhui.project.dal.mybatis.dao.StoreBlindBoxMapper;
import com.wenhui.project.dal.mybatis.dataobject.*;
import com.wenhui.project.web.dto.*;
import com.wenhui.project.web.vo.AdminBoxListVo;
import com.wenhui.project.web.vo.OrderDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品盲盒 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreBlindBoxServiceImpl extends ServiceImpl<StoreBlindBoxMapper, StoreBlindBox> implements StoreBlindBoxService {

    @Resource
    private StoreBlindBoxMapper storeBlindBoxMapper;
    @Resource
    private OrdersService ordersService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private StoreBlindboxGoodsService storeBlindboxGoodsService;

    @Resource
    private UserWinsConfigServiceImpl userWinsConfigService;


    @Resource
    private StoreUserService storeUserService;

    @Resource
    private CoreProxyService coreProxyService;

    private SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyymmddhhmmss");

    @Override
    public List<StoreBlindBoxDto> queryHomeRecommend() {

        return storeBlindBoxMapper.queryHomeRecommend();
    }





    /**
     * 查询单个盲盒
     *
     * @param boxId
     * @return
     */
    @Override
    public List<StoreBlindBoxSingleDto> queryBlindBox(Integer boxId) {
        return storeBlindBoxMapper.queryBlindBox(boxId);
    }

    @Override
    public AdminBoxListVo probabilityBox(Integer boxId) {
        StoreBlindBox b = this.selectById(boxId);
        AdminBoxListVo adminBoxListVo = new AdminBoxListVo();
        BeanUtils.copyProperties(b, adminBoxListVo);
        adminBoxListVo.setRecommend(b.getRecommend()==1?true:false);
        adminBoxListVo.setStatus(b.getStatus()==1?true:false);
        adminBoxListVo.setBoxId(b.getId());
        adminBoxListVo.setThumb(PhotoUrlConver.getPhotoUrl(b.getThumb()));
        List<AdminBoxListVo.BlindBoxProbability> objects = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        objects.add(adminBoxListVo.new BlindBoxProbability("高级商品",0,"0"));
        objects.add(adminBoxListVo.new BlindBoxProbability("稀有商品",0,"0"));
        objects.add(adminBoxListVo.new BlindBoxProbability("史诗商品",0,"0"));
        objects.add(adminBoxListVo.new BlindBoxProbability("传说商品",0,"0"));
        objects.add(adminBoxListVo.new BlindBoxProbability("高级商品",1,df.format(b.getHignProbability())));
        objects.add(adminBoxListVo.new BlindBoxProbability("稀有商品",1,df.format(b.getRarityProbability())));
        objects.add(adminBoxListVo.new BlindBoxProbability("史诗商品",1,df.format(b.getEpicProbability())));
        objects.add(adminBoxListVo.new BlindBoxProbability("传说商品",1,df.format(b.getLegendProbability())));
        return adminBoxListVo;
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetailVo ordersOpenBox(StoreUser storeUser, CoreBoxRule coreBoxRule) {

        OrderDetail orderDetail = orderDetailService.selectOne(new EntityWrapper<OrderDetail>().eq("order_id", orderId).eq("delete_flag", 0));
        Integer boxId = orderDetail.getGoodsId();
        //获取用户自定义中奖概率
        Orders orders = ordersService.selectOne(new EntityWrapper<Orders>().eq("order_id", orderId).eq("delete_flag", 0));
        UserWinsConfig userWinsConfig = userWinsConfigService.selectOne(new EntityWrapper<UserWinsConfig>().eq("delete_flag", 0).eq("box_id", boxId).eq("user_id", orders.getUserId()));
        StoreBlindBox storeBlindBox = this.selectById(boxId);
        StoreBlindboxGoods storeBlindboxGoods1 = openBoxGoods(storeBlindBox, userWinsConfig);
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setOrderId(orderDetail.getOrderId());
        orderDetail1.setGoodsId(storeBlindboxGoods1.getId());
        orderDetail1.setProductName(storeBlindboxGoods1.getName());
        orderDetail1.setProductPrice(storeBlindboxGoods1.getPice());
        orderDetail1.setNumber(1);
        orderDetail1.setProductThumb(storeBlindboxGoods1.getThumb());
        orderDetail.setDeleteFlag(3);
        orderDetailService.updateById(orderDetail);
        orderDetailService.insert(orderDetail1);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        BeanUtils.copyProperties(storeBlindboxGoods1, orderDetailVo);
        boolean b = ordersService.updateForSet("order_status=2", new EntityWrapper<Orders>().eq("order_id", Integer.parseInt(orderId)));
        return orderDetailVo;
    }*/
    /**
     * 获取等级
     * @param coreBoxRule
     * @return
     */
    private Integer getGrade(CoreBoxRule coreBoxRule) {
        List<LotteryGift> lotteryGifts = new ArrayList<LotteryGift>();

        lotteryGifts.add(new LotteryGift(1, "P1", "普通款", Double.parseDouble(coreBoxRule.getOneProbability().toPlainString())));
        lotteryGifts.add(new LotteryGift(2, "P2", "稀有款", Double.parseDouble(coreBoxRule.getTwoProbability().toPlainString())));
        lotteryGifts.add(new LotteryGift(3, "P3", "智尊款", Double.parseDouble(coreBoxRule.getThreeProbability().toPlainString())));

        List<Double> orignalRates = new ArrayList<Double>(lotteryGifts.size());
        for (LotteryGift lotteryGift : lotteryGifts) {
            double probability = lotteryGift.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        //设定抽多少产品
        double num = 1;
        for (int i = 0; i < num; i++) {
            int orignalIndex = LotteryUtil.lottery(orignalRates);
            Integer value = count.get(orignalIndex);
            count.put(orignalIndex, value == null ? 1 : value + 1);
        }

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            return lotteryGifts.get(entry.getKey()).getIndex();
        }
        return 1;
    }

    /**
     * 开始抽
     */
    private StoreBlindboxGoods openBoxNow(CoreBoxRule coreBoxRule) {
        Integer goodGender = getGrade(coreBoxRule);

        EntityWrapper<StoreBlindboxGoods> wrapper = new EntityWrapper<>();
        wrapper.eq("tag", goodGender);
        wrapper.eq("status", 1);
        int count = storeBlindboxGoodsService.selectCount(wrapper);
        //  生成一个随机的int值，0-count之间，要+1
        int index = new Random().nextInt(count)+1;
        wrapper.eq("sort", index);
        StoreBlindboxGoods goods = (StoreBlindboxGoods)storeBlindboxGoodsService.selectOne(wrapper);
        return goods;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) //cusId
    public OrderDetailVo ordersOpenBox(StoreUser storeUser, CoreBoxRule coreBoxRule, Integer type) {
        //type 1是金币抽奖    2是次数抽奖
        if (type == 1){
            //扣除用户次数
            StoreUser storeUserUp = new StoreUser();
            storeUserUp.setGoldAmount(storeUser.getGoldAmount());
            EntityWrapper<StoreUser> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("uid",storeUser.getUid());
            userEntityWrapper.eq("gold_amount",storeUser.getGoldAmount());
            storeUserService.updateById(storeUserUp);
        } else {
            //扣除用户次数
            StoreUser storeUserUp = new StoreUser();
            storeUserUp.setCount(storeUser.getCount());
            EntityWrapper<StoreUser> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("uid",storeUser.getUid());
            userEntityWrapper.eq("count",storeUser.getCount());
            storeUserService.updateById(storeUserUp);
        }

        //随机抽奖
        StoreBlindboxGoods storeBlindboxGoods = openBoxNow(coreBoxRule);
        //记录盲盒操作记录表
        this.saveOrder(storeBlindboxGoods, storeUser);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        BeanUtils.copyProperties(storeBlindboxGoods, orderDetailVo);
        return orderDetailVo;
    }

    private void saveOrder(StoreBlindboxGoods storeBlindboxGoods, StoreUser storeUser){
        Orders orders = new Orders();
        orders.setStoreOrder(2);
        String datetime = yyyymmddhhmmss.format(new Date());
        orders.setOrderNo(datetime + RandomUtils.generateNumberRandom(3));
        orders.setOrderAmountTotal(storeBlindboxGoods.getPice());
        orders.setCreateTime(new Date());
        orders.setOrderStatus(1);
        orders.setPayStatus(1);
        orders.setUserId(UserThreadLocal.get().getUserId().intValue());
        ordersService.insert(orders);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setOrderId(orders.getOrderId());
        orderDetail1.setGoodsId(storeBlindboxGoods.getId());
        orderDetail1.setProductName(storeBlindboxGoods.getName());
        orderDetail1.setProductPrice(storeBlindboxGoods.getPice());
        orderDetail1.setNumber(1);
        orderDetail1.setProductThumb(storeBlindboxGoods.getThumb());
        orderDetail1.setType(storeBlindboxGoods.getTag());
        orderDetailService.insert(orderDetail1);
    }

    @Override
    public Boolean updateBoxAllInfo(AdminStoreBlindBoxDto adminStoreBlindBoxDto) {
//        assert adminStoreBlindBoxDto.getEpicProbability()+adminStoreBlindBoxDto.getHignProbability()+
//                adminStoreBlindBoxDto.getRarityProbability()+adminStoreBlindBoxDto.getLegendProbability()==100:"输入概率和必须为100";

        StoreBlindBox storeBlindBox = new StoreBlindBox();
        BeanUtils.copyProperties(adminStoreBlindBoxDto, storeBlindBox, MyBeanUtils.getNullPropertyNames(adminStoreBlindBoxDto));
        storeBlindBox.setRecommend(adminStoreBlindBoxDto.getRecommend()?1:0);
        storeBlindBox.setThumb(PhotoUrlConver.getPhotoUrlJson(storeBlindBox.getThumb()));
        if (Objects.nonNull(adminStoreBlindBoxDto.getBoxId())){
            storeBlindBox.setId(adminStoreBlindBoxDto.getBoxId());
            boolean update = this.updateById(storeBlindBox);
            return update;
        }else{
            boolean insert = this.insert(storeBlindBox);
            return insert;
        }
    }

    @Override
    public BigDecimal testProfitBox(Integer boxId) {
        BigDecimal bigDecimal = new BigDecimal(0);
        StoreBlindBox storeBlindBox = this.selectById(boxId);
        List<StoreBlindboxGoods> storeBlindboxGoodslist = storeBlindboxGoodsService.selectList(new EntityWrapper<StoreBlindboxGoods>()
                .eq("box_id", storeBlindBox.getId())
//                .eq("tag", OpenBoxEnum.getRecord(openBox.getName()).getType())
        );
        if (storeBlindboxGoodslist.size()==0){
            throw new BusinessException(String.valueOf(ErrorCode.REQUEST_EXCEPTION.getCode()),"盲盒中没有商品,请添加!");
        }
        Map<Integer, List<StoreBlindboxGoods>> collect = storeBlindboxGoodslist.stream().collect(Collectors.groupingBy(StoreBlindboxGoods::getTag));
        if (collect.size()!=4){
            throw new BusinessException(String.valueOf(ErrorCode.REQUEST_EXCEPTION.getCode()),"盲盒中缺少某类型商品,请添加!");
        }
        for (int i = 0; i < 10000; i++) {
            StoreBlindboxGoods storeBlindboxGoods = openBoxGoodsTest(storeBlindBox,null, collect);
            bigDecimal = bigDecimal.add(storeBlindBox.getPrice().subtract(storeBlindboxGoods.getPice()));
        }
        return bigDecimal;
    }

    @Override
    public Page<AdminBoxListVo> getBoxList(PageDto pageDto) {
        Page<AdminBoxListVo> resultPage = new Page<>(pageDto.getCurrentPage(), pageDto.getSize());
        Page<StoreBlindBox> storeBlindBoxPage = this.selectPage(new Page<>(pageDto.getCurrentPage(), pageDto.getSize()),new EntityWrapper<StoreBlindBox>().ne("status",2));
        List<AdminBoxListVo> collect = storeBlindBoxPage.getRecords().stream().sorted(Comparator.comparing(StoreBlindBox::getSort)).map(b -> {
            AdminBoxListVo adminBoxListVo = new AdminBoxListVo();
            BeanUtils.copyProperties(b, adminBoxListVo);
            adminBoxListVo.setRecommend(b.getRecommend()==1?true:false);
            adminBoxListVo.setStatus(b.getStatus()==1?true:false);
            adminBoxListVo.setBoxId(b.getId());
            adminBoxListVo.setThumb(PhotoUrlConver.getPhotoUrl(b.getThumb()));
            List<AdminBoxListVo.BlindBoxProbability> objects = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("#.######");
            df.setRoundingMode(RoundingMode.HALF_DOWN);
            objects.add(adminBoxListVo.new BlindBoxProbability("高级商品",0,"0"));
            objects.add(adminBoxListVo.new BlindBoxProbability("稀有商品",0,"0"));
            objects.add(adminBoxListVo.new BlindBoxProbability("史诗商品",0,"0"));
            objects.add(adminBoxListVo.new BlindBoxProbability("传说商品",0,"0"));
            objects.add(adminBoxListVo.new BlindBoxProbability("高级商品",1,df.format(b.getHignProbability())));
            objects.add(adminBoxListVo.new BlindBoxProbability("稀有商品",1,df.format(b.getRarityProbability())));
            objects.add(adminBoxListVo.new BlindBoxProbability("史诗商品",1,df.format(b.getEpicProbability())));
            objects.add(adminBoxListVo.new BlindBoxProbability("传说商品",1,df.format(b.getLegendProbability())));
            adminBoxListVo.setBox_probability(objects);
            return adminBoxListVo;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        resultPage.setTotal(storeBlindBoxPage.getTotal());
        return resultPage;
    }

    @Override
    public Boolean changeProSettingUsed(Integer boxId, Integer type, Boolean used) {
        boolean result = false;
        List<StoreBlindboxGoods> storeBlindboxGoodslist = storeBlindboxGoodsService.selectList(new EntityWrapper<StoreBlindboxGoods>()
                        .eq("box_id", boxId)
//                .eq("tag", OpenBoxEnum.getRecord(openBox.getName()).getType())
        );
        if (storeBlindboxGoodslist.size()==0){
            throw new BusinessException(String.valueOf(ErrorCode.REQUEST_EXCEPTION.getCode()),"盲盒中没有商品,请添加!");
        }
        Map<Integer, List<StoreBlindboxGoods>> collect = storeBlindboxGoodslist.stream().collect(Collectors.groupingBy(StoreBlindboxGoods::getTag));
        if (collect.size()!=4){
            throw new BusinessException(String.valueOf(ErrorCode.REQUEST_EXCEPTION.getCode()),"盲盒中缺少某类型商品,请添加!");
        }
        if (1==type){
            result = this.updateForSet(used?"status=1":"status=0",new EntityWrapper<StoreBlindBox>().eq("id",boxId));
        }
        if (2==type){
            result = this.updateForSet(used?"recommend=1":"recommend=0",new EntityWrapper<StoreBlindBox>().eq("id",boxId));
        }
        return result;
    }

    @Override
    public Boolean deleteBox(Integer boxId) {
        boolean result = this.updateForSet("status=2",new EntityWrapper<StoreBlindBox>().eq("id",boxId));
        return result;
    }

    /**
     * 根据盲盒id获取盲盒的概率
     *
     * @param boxId
     * @return
     */
    @Override
    public BlindBoxRatioDto queryBlindBoxRatio(Integer boxId) {
        if (boxId==null&&boxId<=0){
            throw new BusinessException(ErrorCode.SERVER_ERROR.toString(),"未获取到盲盒id");
        }
        StoreBlindBox storeBlindBox = storeBlindBoxMapper.selectOne(new StoreBlindBox().setId(boxId.longValue()));
        BlindBoxRatioDto blindBoxRatioDto = new BlindBoxRatioDto();
        BeanUtils.copyProperties(storeBlindBox,blindBoxRatioDto);
        return blindBoxRatioDto;
    }

    /**
     * 首页盲盒列表
     *
     * @param cateId
     * @return
     */
    @Override
    public List<HomeBoxGoods> searchHomeBoxList(Integer cateId) {
        ArrayList<HomeBoxGoods> homeBoxGoods = new ArrayList<>();
        List<StoreBlindBox> storeBlindBoxes = this.selectList(new EntityWrapper<StoreBlindBox>().eq(!ObjectUtils.isEmpty(cateId) && cateId > 0, "category_id", cateId).eq("status", 1));
        for (StoreBlindBox storeBlindBox : storeBlindBoxes){
            HomeBoxGoods boxGoods = new HomeBoxGoods();
            BeanUtils.copyProperties(storeBlindBox,boxGoods);
            int i = storeBlindboxGoodsService.selectCount(new EntityWrapper<StoreBlindboxGoods>()
                    .eq("box_id", storeBlindBox.getId())
                    .eq("delete_flag", 0));
            boxGoods.setGoodsCount(i);
            boxGoods.setLowestPrice(i>0?Math.round(storeBlindboxGoodsService.selectObjs(new EntityWrapper<StoreBlindboxGoods>()
                    .setSqlSelect("min(pice)")
                    .eq("box_id",storeBlindBox.getId())
                    .eq("delete_flag",0)).stream()
                        .findFirst()
                        .map(obj -> obj instanceof Double ? (Double) obj:Double.parseDouble(obj.toString()))
                        .orElse(0.00).floatValue()) :0);
            boxGoods.setHighestPrice(i>0?Math.round(storeBlindboxGoodsService.selectObjs(new EntityWrapper<StoreBlindboxGoods>()
                            .setSqlSelect("max(pice)")
                            .eq("box_id", storeBlindBox.getId())
                            .eq("delete_flag", 0)).stream()
                    .findFirst()
                    .map(obj -> obj instanceof Double ? (Double) obj : Double.parseDouble(obj.toString()))
                    .orElse(0.00).floatValue()):0);
            homeBoxGoods.add(boxGoods);
        }
        return homeBoxGoods;
    }

    private StoreBlindboxGoods openBoxGoods(StoreBlindBox storeBlindBox,UserWinsConfig userWinsConfig) {

        List<OpenBox> openBoxes = new ArrayList<>();
        Double hignProbability = storeBlindBox.getHignProbability();
        Double rarityProbability = storeBlindBox.getRarityProbability();
        Double epicProbability = storeBlindBox.getEpicProbability();
        Double legendProbability = storeBlindBox.getLegendProbability();
        if (Objects.nonNull(userWinsConfig)){
            hignProbability = userWinsConfig.getHignProbability();
            rarityProbability = userWinsConfig.getRarityProbability();
            epicProbability = userWinsConfig.getEpicProbability();
            legendProbability = userWinsConfig.getLegendProbability();
        }
        openBoxes.add(new OpenBox("高级商品", hignProbability));
        openBoxes.add(new OpenBox("稀有商品", rarityProbability));
        openBoxes.add(new OpenBox("史诗商品", epicProbability));
        openBoxes.add(new OpenBox("传说商品", legendProbability));
        OpenBoxUtil.verfypercent(hignProbability,rarityProbability,epicProbability,legendProbability);
        OpenBox openBox = OpenBoxUtil.openBox(openBoxes);
        List<StoreBlindboxGoods> storeBlindboxGoods = storeBlindboxGoodsService.selectList(new EntityWrapper<StoreBlindboxGoods>()
                        .eq("box_id", storeBlindBox.getId())
                .eq("tag", OpenBoxEnum.getRecord(openBox.getName()).getType())
        );
        int index = new Random().nextInt(storeBlindboxGoods.size());
        StoreBlindboxGoods storeBlindboxGoods1 = storeBlindboxGoods.get(index);
        return storeBlindboxGoods1;
    }

    private StoreBlindboxGoods openBoxGoodsTest(StoreBlindBox storeBlindBox,UserWinsConfig userWinsConfig,Map<Integer, List<StoreBlindboxGoods>> collect) {

        List<OpenBox> openBoxes = new ArrayList<>();
        Double hignProbability = storeBlindBox.getHignProbability();
        Double rarityProbability = storeBlindBox.getRarityProbability();
        Double epicProbability = storeBlindBox.getEpicProbability();
        Double legendProbability = storeBlindBox.getLegendProbability();
        if (Objects.nonNull(userWinsConfig)){
            hignProbability = userWinsConfig.getHignProbability();
            rarityProbability = userWinsConfig.getRarityProbability();
            epicProbability = userWinsConfig.getEpicProbability();
            legendProbability = userWinsConfig.getLegendProbability();
        }
        openBoxes.add(new OpenBox("高级商品", hignProbability));
        openBoxes.add(new OpenBox("稀有商品", rarityProbability));
        openBoxes.add(new OpenBox("史诗商品", epicProbability));
        openBoxes.add(new OpenBox("传说商品", legendProbability));
        OpenBoxUtil.verfypercent(hignProbability,rarityProbability,epicProbability,legendProbability);
        OpenBox openBox = OpenBoxUtil.openBox(openBoxes);
        List<StoreBlindboxGoods> storeBlindboxGoods = collect.get(OpenBoxEnum.getRecord(openBox.getName()).getType());
        int index = new Random().nextInt(storeBlindboxGoods.size());
        StoreBlindboxGoods storeBlindboxGoods1 = storeBlindboxGoods.get(index);
        return storeBlindboxGoods1;
    }
}
