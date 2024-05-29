package com.wenhui.project.biz.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.enums.PayTyprEnum;
import com.wenhui.common.base.payment.Yzf;
import com.wenhui.common.base.payment.ZfbPay;
import com.wenhui.common.base.payment.Zhifule;
import com.wenhui.common.base.utils.DataDesensitizedUtils;
import com.wenhui.common.base.utils.QRCodeUtil;
import com.wenhui.common.base.utils.RedisUtil;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.base.utils.RandomUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.integration.pay.alipay.AlipayServiceCall;
import com.wenhui.integration.pay.alipay.AlipayServiceCallConstant;
import com.wenhui.project.biz.config.OrdersRebateConfig;
import com.wenhui.project.biz.config.RebatePart;
import com.wenhui.project.biz.enums.BizConfigKeyEnum;
import com.wenhui.project.biz.service.*;
import com.wenhui.project.dal.mybatis.dao.OrderDetailMapper;
import com.wenhui.project.dal.mybatis.dao.OrdersMapper;
import com.wenhui.project.dal.mybatis.dataobject.*;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.*;
import com.wenhui.project.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户订单总表 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private CityService cityService;
    @Autowired
    private OrdersPayRequestLogService ordersPayRequestLogService;

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private PaymentChannelService paymentChannelService;

    @Resource
    private PaymentMethodService paymentMethodService;

    @Resource
    private OrdersRebateService ordersRebateService;

    @Resource
    private OrderLogisticsService orderLogisticsService;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private StoreAddressService storeAddressService;

    @Autowired
    private StoreBlindBoxService storeBlindBoxService;

    @Autowired
    private StoreUserService storeUserService;

    @Resource
    SysDictItemService sysDictItemService;

    @Resource
    StoreBlindboxGoodsService storeBlindboxGoodsService;

    @Resource
    StoreGoodsService storeGoodsService;

    @Resource
    UserWinsConfigService userWinsConfigService;

    @Autowired
    private RedisUtil redisUtil;

    private SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyymmddhhmmss");

    private OrdersRebateConfig ordersRebateConfig = new OrdersRebateConfig();

    /**
     * @param type
     * @return
     * @Override 1:支付宝
     * 2:微信支付
     * 3:支付宝当面付
     * 4:QQ钱包
     */
    @Transactional(rollbackFor = Exception.class)
    public void orderPaymentCall(HttpServletRequest request, HttpServletResponse response, String type, Integer boxId) {
        OrderPaymentCallDto orderPaymentCallDto = new OrderPaymentCallDto();
        String datetime = yyyymmddhhmmss.format(new Date());
        orderPaymentCallDto.setPayId(datetime + RandomUtils.generateNumberRandom(3));
        switch (type) {
            case "1":
                orderPaymentCallDto.setType(PayTyprEnum.ALIPAY.getCode());
                break;
            case "2":
                orderPaymentCallDto.setType(PayTyprEnum.WXPAY.getCode());
                break;
            case "3":
                orderPaymentCallDto.setType(PayTyprEnum.DMF.getCode());
                break;
            case "4":
                orderPaymentCallDto.setType(PayTyprEnum.QQPAY.getCode());
                break;
            default:
                throw new BusinessException(String.valueOf(ErrorCode.METHOD_NOT_ALLOWED), "支付方式错误");
        }
        List payResult = new AlipayServiceCall().AlipaySubmit(orderPaymentCallDto, ordersPayRequestLogService);
        //[{"date":1676525787,"uid":3697,"payType":1,"orderId":"202302161302276186","price":0.01,"outTradeNo":"202302161302276186","reallyPrice":"0.01","isAuto":1,"payUrl":"wxp://f2f0TVzKge5GvD0giekm1o-I7un5yFFd3YoPYg--0vcKNx9wW_yD6UJEexeaRuH6kQne","payId":"20233616013624938","state":0,"timeOut":3}]
        if (CollectionUtils.isEmpty(payResult)) {
            throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
        }
        OrderPaymentCallResponseDto orderPaymentCallResponseDto = JSON.parseObject(payResult.get(0).toString(), OrderPaymentCallResponseDto.class);
        //创建订单
        Orders orders = createBoxOrder(orderPaymentCallDto, orderPaymentCallResponseDto, boxId);
        String payUrl = orderPaymentCallResponseDto.getPayUrl();
        QRCodeUtil.getQRcodeStream(response, payUrl, orders.getOrderNo());
    }

    private Orders createBoxOrder(OrderPaymentCallDto orderPaymentCallDto, OrderPaymentCallResponseDto orderPaymentCallResponseDto, Integer boxId) {
        Orders orders = new Orders();
        orders.setStoreOrder(2);
        orders.setOrderNo(orderPaymentCallDto.getPayId());
        orders.setOrderAmountTotal(orderPaymentCallDto.getPrice());
        orders.setProductAmountTotal(orderPaymentCallDto.getPrice());
        orders.setShippingCode(orderPaymentCallResponseDto.getOrderId());
        orders.setCreateTime(new Date());
        //TODO 项目数量
        orders.setProductCount(1);
        orders.setPayChannel(AlipayServiceCallConstant.URL);
        orders.setOutTradeNo(orderPaymentCallResponseDto.getOutTradeNo());
        orders.setUserId(UserThreadLocal.get().getUserId().intValue());
        orders.setPayType(orderPaymentCallResponseDto.getPayType());
//        orders.setOrderStatus(1);
        boolean insert = this.insert(orders);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orders.getOrderId());
        orderDetail.setGoodsId(boxId);
        orderDetail.setProductName("盲盒");
        orderDetail.setProductPrice(orderPaymentCallDto.getPrice());
        orderDetail.setNumber(1);
        orderDetailService.insert(orderDetail);

        return orders;
    }


    private Orders createBoxOrder(OrderInfoVo orderInfo,PayParamDto payParamDto, OrderPaymentCallDto orderPaymentCallResponseDto) {
        Orders orders = new Orders();
        orders.setStoreOrder(orderInfo.getProductType());
        orders.setOrderNo(payParamDto.getOrderNo());
//        orders.setOrderAmountTotal(orderPaymentCallResponseDto.getPrice());
        orders.setProductAmountTotal(payParamDto.getPrice());
        orders.setCreateTime(new Date());
//        //TODO 项目数量
        orders.setProductCount(1);
        orders.setPayChannel(orderInfo.getChannelId());
        orders.setUserId(UserThreadLocal.get().getUserId().intValue());
        switch (orderInfo.getPaymentMethodId()){
            case  "zhifule":
                orders.setShippingCode(orderPaymentCallResponseDto.getPayId());
                //第三方支付订单号
                orders.setOutTradeNo(orderPaymentCallResponseDto.getPayId());
            break;
            case "wxpay":
                break;
            case "zfbpay":
                break;
        }
        orders.setPayType(payParamDto.getPayTypeID());
        orders.setOrderStatus(1);
        orders.setPayStatus(0);
        boolean orderinsert = this.insert(orders);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orders.getOrderId());
        orderDetail.setGoodsId(Integer.valueOf(orderInfo.getProductId()));
        orderDetail.setProductName(payParamDto.getTradeName()+" 盲盒");
        orderDetail.setProductPrice(payParamDto.getPrice());
        orderDetail.setNumber(1);
        boolean orderdetail = orderDetailService.insert(orderDetail);

        //用户抽奖概率表增加数据
        UserWinsConfig userWinsConfiginfo = userWinsConfigService.selectOne(new EntityWrapper<UserWinsConfig>().eq("delete_flag", 0).eq("user_id",orders.getUserId()).eq("box_id",orderInfo.getProductId()));
            UserWinsConfig userWinsConfig = new UserWinsConfig();
        if (BeanUtil.isNotEmpty(userWinsConfiginfo)){
            Integer integer = userWinsConfiginfo.getPurchasesNum();
            userWinsConfig.setPurchasesNum(++integer);
            boolean update = userWinsConfigService.update(userWinsConfig, new EntityWrapper<UserWinsConfig>().eq("id", userWinsConfiginfo.getId()).eq("user_id", orders.getUserId()).eq("box_id", orderInfo.getProductId()));
            log.info("用户抽奖概率表修改数据{}",update);
        }else {
            //表中没有这个盲盒的开盒数据
            //获取盲盒的开奖概率
            StoreBlindBox storeBlindBox = storeBlindBoxService.selectOne(new EntityWrapper<StoreBlindBox>().eq("id", orderInfo.getProductId()).eq("status", 1));
            BeanUtils.copyProperties(storeBlindBox, userWinsConfig);
            userWinsConfig.setPurchasesNum(1);
            userWinsConfig.setUserId(orders.getUserId());
            userWinsConfig.setBoxId(Integer.valueOf(orderInfo.getProductId()));
            boolean insert = userWinsConfigService.insert(userWinsConfig);
            log.info("用户抽奖概率表增加数据{}", insert);
        }
        return orders;
    }


    /**
     * 下单接口
     *
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResultDto placeOrder(HttpServletRequest request,OrderInfoVo orderInfo) {
        if (orderInfo.getProductType()==null) {
            throw new BusinessException("500001","支付渠道未获取到");
        }
        PayParamDto payParamDto = new PayParamDto();
        payParamDto.setClientIp(request.getRemoteAddr());
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        //判断是实物还是盲盒
        switch (orderInfo.getProductType()){
            case 1:
                StoreGoods storeGoods = storeGoodsService.selectOne(new EntityWrapper<StoreGoods>().eq("goods_id", orderInfo.getProductId()));
                if (BeanUtil.isEmpty(storeGoods))
                    throw new BusinessException("500002","没有获取到对应的商品");
                //实物价格
                payParamDto.setPrice(storeGoods.getGoodsPrice());
                //实物商品名称
                payParamDto.setTradeName(storeGoods.getGoodsName());
                break;
            case 2:
                StoreBlindBox storeBlindBox = storeBlindBoxService.selectOne(new EntityWrapper<StoreBlindBox>().eq("status", 1).eq("id", orderInfo.getProductId()));
                if (BeanUtil.isEmpty(storeBlindBox))
                    throw new BusinessException("500002","没有获取到对应的盲盒");
                //盲盒价格
                payParamDto.setPrice(storeBlindBox.getPrice());
                //盲盒名称
                payParamDto.setTradeName(storeBlindBox.getTitle());
                break;
        }

        //支付渠道id
        String channelId = orderInfo.getChannelId();
        if (StringUtils.isEmpty(channelId)) {
            throw new BusinessException("500003","支付渠道未获取到");
        }
        //获取支付渠道的信息
        PaymentChannel paymentChannel = paymentChannelService.selectOne(new EntityWrapper<PaymentChannel>().eq("CHANNEL_ID", channelId).eq("CHANNEL_STATE", 0));
        if (BeanUtil.isEmpty(paymentChannel))
            throw new BusinessException("500004","支付渠道未获取到");
        //获取支付方式
        PaymentMethod paymentMethod = paymentMethodService.selectOne(new EntityWrapper<PaymentMethod>().eq("CHANNEL_ID", paymentChannel.getChannelId()).eq("pay_state", 0).eq("pay_id",orderInfo.getPaymentMethodId().trim()));
        //设置uid
        payParamDto.setUid(paymentChannel.getMerchantId());
       //公钥
        payParamDto.setPublicKey(paymentChannel.getPublicKey());
        //网关URl
        payParamDto.setChannelUrl(paymentChannel.getChannelUrl());

        //私钥
        payParamDto.setPrivateKey(paymentChannel.getPrivateKey());
        //同步回调URL
        payParamDto.setReturnUrl(paymentChannel.getSyncUrl());
        //异步回调URL
        payParamDto.setNotifyUrl(paymentChannel.getAsynUrl());
        //订单号
        //商户订单号
        String format = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        String numbers = RandomUtil.randomNumbers(5);
        String orderno = format+numbers;
        payParamDto.setOrderNo(orderno);
        //支付方式
        payParamDto.setPayTypeID(paymentMethod.getId());
        payParamDto.setPayType(orderInfo.getPaymentMethodId());
        OrderResultDto orderResultDto = new OrderResultDto();

        switch (paymentChannel.getChannelType()){
            case "zhifule":
                Zhifule zhifule = new Zhifule();
                PaymentResultBo pay = zhifule.pay(payParamDto,ordersPayRequestLogService);
                log.info(pay.toString());
                if (pay.getJson().isEmpty()) {
                    throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
                }
                OrderPaymentCallDto orderPaymentCallResponseDto = JSON.parseObject(pay.getJson().toString(), OrderPaymentCallDto.class);
                //创建订单
                Orders boxOrder = createBoxOrder(orderInfo, payParamDto, orderPaymentCallResponseDto);
//                String payUrl = orderPaymentCallResponseDto.get();

//                orderResultDto.setPayInfo(Base64.encode(payUrl));
                orderResultDto.setPayInfo(pay.getResquestInfo());
                orderResultDto.setOrderNo(boxOrder.getOrderId().toString());
                orderResultDto.setResultType(pay.getResultType());
                break;
            case "wxpay":
                Orders wxpay = createBoxOrder(orderInfo, payParamDto, null);
                StringBuffer sb=new StringBuffer();
                sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx32e1fae109039c15&redirect_uri=");
                sb.append(payParamDto.getReturnUrl());
                sb.append("&response_type=code&scope=snsapi_base&state=");
                sb.append(wxpay.getOrderId());
                sb.append("#wechat_redirect");
                String qRcodeStream = QRCodeUtil.getQRcodeStream(sb.toString(), null);
                orderResultDto.setPayInfo(qRcodeStream);
                orderResultDto.setOrderNo(wxpay.getOrderNo().toString());
                orderResultDto.setResultType(2);
                //企业微信
                break;
            case "zfbpay":
                //企业支付宝
                PaymentResultBo zfbpay = new ZfbPay().pay(payParamDto, ordersPayRequestLogService);
                if (BeanUtil.isEmpty(zfbpay)) {
                    throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
                }
                //创建订单
                Orders boxOrder1 = createBoxOrder(orderInfo, payParamDto, null);
                orderResultDto.setPayInfo(zfbpay.getResquestInfo());
                orderResultDto.setOrderNo(boxOrder1.getOrderId().toString());
                orderResultDto.setResultType(zfbpay.getResultType());
                break;
            case "yzf":{
                PaymentResultBo yzfresult = new Yzf().pay(payParamDto, ordersPayRequestLogService);
                if (BeanUtil.isEmpty(yzfresult)) {
                    throw new BusinessException(String.valueOf(ErrorCode.SYSTEM_EXCEPTION), "支付未响应");
                }
                //创建订单
                if(yzfresult.getCode()==10000){
                    JSONObject json = yzfresult.getJson();
                    String json1 = (String) json.get("sign");
                    boolean set = redisUtil.set("yzf" + payParamDto.getOrderNo(), json1, 300);
                    Orders boxOrderyzf = createBoxOrder(orderInfo, payParamDto, null);
                    orderResultDto.setPayInfo(yzfresult.getResquestInfo());
                    orderResultDto.setOrderNo(boxOrderyzf.getOrderId().toString());
                    orderResultDto.setResultType(yzfresult.getResultType());
                }
                break;
            }
        }
        return orderResultDto;
    }




    @Override
    public Boolean orderPayStatus(String orderNo) {
        Orders orders = this.selectOne(new EntityWrapper<Orders>().eq("order_no", orderNo));
        if (BeanUtil.isEmpty(orders)){
            return false;
        }
        Integer payStatus = orders.getPayStatus();
        return payStatus == 1 ? true : false;
    }

    @Override
    public List<OrdersListDto> ordersList(Integer type) {
        int i = UserThreadLocal.get().getUserId().intValue();
        return ordersMapper.queryList(type,i);
    }

    public static void main(String[] args) {
       int num=0;
        for (int i=0;i<10000000;i++) {
//            int randomInt = RandomUtil.randomInt(100);
//            int randomFloat = RandomUtil.randomInt(10000);
//            Double probability = Double.valueOf(randomInt + "." + String.format("%04d", randomFloat));
            int randomFloat1 = RandomUtil.randomInt(1000000);
            if (randomFloat1==1){
                System.out.println(1);
            }
            Double probability = Double.valueOf(randomFloat1/10000 + "." +  String.format("%04d", randomFloat1%10000));
//            System.out.println(probability >= 0 && probability <= 0.0001);
            if(probability >= 0 && probability <= 0.0001){
                num=++num;
                System.out.println(probability);
            }
        }
        System.out.println("累计抽中：【"+num+"】次");


            // 设置四个概率变量
            double percent1 = 0.0001;
            double percent2 = 0.0002;
            double percent3 = 0.0003;
            double percent4 = 99.999;

// 将概率值乘以1000000，转化为整数
            int intPercent1 = (int) (percent1 * 1000000);
            int intPercent2 = (int) (percent2 * 1000000);
            int intPercent3 = (int) (percent3 * 1000000);
            int intPercent4 = 1000000000 - intPercent1 - intPercent2 - intPercent3;

// 检查概率之和是否为100%
            if (intPercent1 + intPercent2 + intPercent3 + intPercent4 != 1000000000) {
                System.out.println("概率之和不等于100%，请重新设置");
            } else {
                int num1=0;
                int num2=0;
                int num3=0;
                int num4=0;
                for (int i=0;i<10000000;i++){
                    // 生成一个0到999999999之间的随机整数
                    int rand = (int) (Math.random() * 1000000000);

                    // 根据概率区间判断抽中的物品
                    if (rand < intPercent1) {
                        num1=++num1;
                        System.out.println("抽到了第1个物品");
                    } else if (rand < intPercent1 + intPercent2) {
                        num2=++num2;
                        System.out.println("抽到了第2个物品");
                    } else if (rand < intPercent1 + intPercent2 + intPercent3) {
                        num3=++num3;
                        System.out.println("抽到了第3个物品");
                    } else {
                        num4=++num4;
//                        System.out.println("抽到了第4个物品");
                    }
                }
                System.out.println("星耀款：["+num1+"] 钻石款:["+num2+"] 黄金款:["+num3+"] 白银款:["+num4+"]");
            }





        }

    @Override
    public Boolean orderPaymentStatus(Map<String,String> orderPaymentStatusDto) {
        boolean result = false;
        if (Objects.nonNull(orderPaymentStatusDto)) {
            try {
                String sign = orderPaymentStatusDto.get("payId") + orderPaymentStatusDto.get("param") + orderPaymentStatusDto.get("type") + orderPaymentStatusDto.get("price") + orderPaymentStatusDto.get("reallyPrice") + AlipayServiceCallConstant.KEY;
                String md5 = DigestUtils.md5Hex(sign);
                assert md5.equals(orderPaymentStatusDto.get("sign")) : "支付回调数据异常";
                String orderNo = orderPaymentStatusDto.get("payId");
                Orders order = this.selectOne(new EntityWrapper<Orders>().eq("order_no", orderNo));
                if (Objects.isNull(order)) {
                    throw new BusinessException(ErrorCode.SYSTEM_EXCEPTION.getCode(),"未查询到订单");
                }
                assert orderPaymentStatusDto.get("price").equals(order.getProductAmountTotal()) : "支付价格不同";
                assert orderPaymentStatusDto.get("type").equals(order.getPayType())  : "支付方式不同";
                PaymentMethod paymentMethod = new PaymentMethod();
                switch (orderPaymentStatusDto.get("type")){
                    case "2":
                        paymentMethod=  paymentMethodService.selectOne(new EntityWrapper<PaymentMethod>().eq("CHANNEL_ID", "zhifule").eq("pay_state",0).like("pay_name", "%支付宝%"));
                        break;
                    case "1":
                        paymentMethod=  paymentMethodService.selectOne(new EntityWrapper<PaymentMethod>().eq("CHANNEL_ID", "zhifule").eq("pay_state",0).like("pay_name", "%微信%"));
                        break;
                    default:
                        break;
                }
                order.setOutTradeNo(orderPaymentStatusDto.get("payId"));
                order.setPayType(paymentMethod.getId());
                order.setOrderStatus(1);
                order.setPayStatus(1);
                order.setOrderAmountTotal(new BigDecimal(orderPaymentStatusDto.get("reallyPrice")));
                order.setPaymentTime(new Date());
                result = this.updateById(order);
                //TODO 参数为总利润
                RebatePart rebate = ordersRebateConfig.getRebate(order.getOrderAmountTotal());

                if (storeUserService.selectById(order.getUserId()).getType()!=1){
                    ordersRebateService.insert(new OrdersRebate(order.getUserId(), order.getOrderId(),order.getProductAmountTotal(),new BigDecimal(0),rebate.getUserRecommendRebate(),rebate.getTerraceRebate().add(rebate.getUserRebate())));
                }else{
                    ordersRebateService.insert(new OrdersRebate(order.getUserId(), order.getOrderId(),order.getProductAmountTotal(),rebate.getUserRebate(),rebate.getUserRecommendRebate(),rebate.getTerraceRebate()));
                }
            } catch (AssertionError assertionError) {
                log.error(assertionError.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ordersPayRequestLogService.insert(new OrdersPayRequestLog("/pay/status", JSONObject.toJSONString(orderPaymentStatusDto), 2));
            }
        }
        return result;
    }

    /**
     * 去取货接口
     *
     * @param orderNo
     * @return
     */
    @Override
    public PickupGoodsDto pickupGoods(String orderNo) {
        PickupGoodsDto pickupGoodsDto = new PickupGoodsDto();
        //获取订单商品表的商品详细信息
        OrderDetail orderDetail = orderDetailService.selectOne(new EntityWrapper<OrderDetail>().eq("order_id", orderNo).eq("delete_flag", 0));
        if (!BeanUtil.isEmpty(orderDetail)) {
            BeanUtils.copyProperties(orderDetail, pickupGoodsDto);
            //获取用户默认收获地址，没有默认收获地址，就选地址列表中第一条，按创建时间排序
            UserAddressListVo userAddressListVo = storeAddressService.defaultAddress(String.valueOf(UserThreadLocal.get().getUserId().intValue()));
            if (!BeanUtil.isEmpty(userAddressListVo))
                pickupGoodsDto.setUserAddress(userAddressListVo);
        }
        if (BeanUtil.isEmpty(pickupGoodsDto))
            throw new BusinessException(String.valueOf(ErrorCode.DATA_EMPTY_DATA), "未查询到相关数据");
        return pickupGoodsDto;
    }

    /**
     * 获取单个订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OneOrderDetailDto queryOneOrder(Integer orderId) {
        OneOrderDetailDto oneOrderDetailDto = new OneOrderDetailDto();
        int uid = UserThreadLocal.get().getUserId().intValue();
        //获取当前用户传入订单id唯一订单
        Orders orders = this.selectOne(new EntityWrapper<Orders>().eq("user_id", uid).eq("order_id", orderId).eq("delete_flag", 0).in("order_status",new Integer[]{3,4}));
        if (BeanUtil.isEmpty(orders)){
            throw new BusinessException("4000006", "订单不存在");
        }
        BeanUtils.copyProperties(orders,oneOrderDetailDto);
        //获取订单的收货地址列表
        OrderLogistics orderLogistics = orderLogisticsService.selectOne(new EntityWrapper<OrderLogistics>().eq("delete_flag", 0).eq("order_id", orderId));
        if (BeanUtil.isEmpty(orderLogistics)){
            throw new BusinessException("400007", "订单不存在物流地址");
        }
        AddressOneOrder userAddressDto = new AddressOneOrder();
        BeanUtils.copyProperties(orderLogistics,userAddressDto);
        oneOrderDetailDto.setUserAddressDto(userAddressDto);
        //获取订单里面的商品列表
        List<OrderDetail> orderDetails = orderDetailService.selectList(new EntityWrapper<OrderDetail>().eq("delete_flag", 0).eq("order_id", orderId));
        if (orderDetails.size()>0){
            List<OrderGoodsListDto> listDtos = new ArrayList<>();
            orderDetails.stream().forEach(a->{
                OrderGoodsListDto orderGoodsListDto = new OrderGoodsListDto();
                BeanUtils.copyProperties(a,orderGoodsListDto);
                listDtos.add(orderGoodsListDto);
            });
            oneOrderDetailDto.setListDtos(listDtos);
        }
        return oneOrderDetailDto;
    }

    /**
     * 提交提货信息
     *
     * @param orderId
     * @param addressId
     * @return
     */
    @Override
    public Boolean insertPickupGoods(Integer orderId, Integer addressId) {
        int uid = UserThreadLocal.get().getUserId().intValue();
        //获取订单表物流表是否存在物流信息
        List<OrderLogistics> orderLogistics1 = orderLogisticsService.selectList(new EntityWrapper<OrderLogistics>().eq("delete_flag", 0).eq("order_id", orderId));
        if (orderLogistics1.size() > 0) {
            throw new BusinessException("40003", "订单已经产生收货地址信息");
        }
        //地址列表获取地址信息
        StoreAddress storeAddress = storeAddressService.selectOne(new EntityWrapper<StoreAddress>().eq("id", addressId).eq("uid", uid).eq("delete_flag", 0));
        if (BeanUtil.isEmpty(storeAddress)) {
            throw new BusinessException("40004", "没有在用户地址列表中获取到该地址信息");
        }
        OrderLogistics orderLogistics = new OrderLogistics();
        String province = cityService.selectOne(new EntityWrapper<City>().eq("code", storeAddress.getProvince())).getName();
        String city = cityService.selectOne(new EntityWrapper<City>().eq("code", storeAddress.getCity())).getName();
        String region = cityService.selectOne(new EntityWrapper<City>().eq("code", storeAddress.getRegion())).getName();
        StringBuffer addressString = new StringBuffer();
        addressString.append(province).append(city).append(region).append(storeAddress.getAddress());
        orderLogistics.setConsigneeTelphone(storeAddress.getMoblie());
        orderLogistics.setConsigneeRealname(storeAddress.getContact());
        orderLogistics.setConsigneeAddress(addressString.toString());
        orderLogistics.setOrderId(orderId);
        //用户提货后，在用户地址表中添加收货地址信息
        boolean insert = orderLogisticsService.insert(orderLogistics);
//        boolean insert =true;
        Orders orders = new Orders();
        //3待发货3、已发货
        orders.setOrderStatus(3);
        orders.setAddress(addressString.toString());
        orders.setAddressId(storeAddress.getId());
        boolean update = this.update(orders, new EntityWrapper<Orders>().eq("order_id", orderId).eq("user_id", uid).eq("delete_flag", 0));
//        boolean update = true;
        if (insert && update) {
            return true;
        }
        return false;
    }

    @Override
    public GetAllOrderDetailVo getAllOrderDetail() {
        JwtUser jwtUser = UserThreadLocal.get();
        int order_count = this.selectCount(new EntityWrapper<Orders>()
                .eq("user_id", jwtUser.getUserId().intValue())
                .eq("delete_flag", 0)
                .eq("store_order", 1));
        GetAllOrderDetailVo getAllOrderDetailVo = new GetAllOrderDetailVo();
        getAllOrderDetailVo.setOrderSum(order_count);
        getAllOrderDetailVo.setCardSum(0);
        return getAllOrderDetailVo;
    }

    @Override
    public GetOperateDataVo getOperateData(String startTime, String endTime) {
        int userId = UserThreadLocal.get().getUserId().intValue();
        GetOperateDataVo getOperateDataVo = new GetOperateDataVo();
        JwtUser jwtUser = UserThreadLocal.get();
        if (jwtUser.getUserId().intValue()==0){
            getOperateDataVo.setDuihuan(ordersRebateService.getTerraceRebate(0, startTime, endTime));
            getOperateDataVo.setFenjie(ordersRebateService.getTerraceRebate(1, startTime, endTime));
        }else{
            getOperateDataVo.setDuihuan(ordersRebateService.getUserRebateById(userId, startTime, endTime));
            getOperateDataVo.setFenjie(ordersRebateService.getUserSpreadRebateById(userId, startTime, endTime));
        }
        getOperateDataVo.setTiqu(getOperateDataVo.getDuihuan().add(getOperateDataVo.getFenjie()).setScale(2, BigDecimal.ROUND_HALF_UP));
        return getOperateDataVo;
    }

    @Override
    public AdminOrderListVo getOrderList(String user_id, String startTime, String endTime) {
        AdminOrderListVo adminOrderListVo = new AdminOrderListVo();
        List<Orders> orders = this.selectList(new EntityWrapper<Orders>()
                .eq("user_id", user_id)
                .eq("store_order", 2)
                .eq("delete_flag", 0)
                .eq("pay_status", 1)
                .ge(StringUtils.isNotEmpty(startTime), "create_time", startTime)
                .le(StringUtils.isNotEmpty(endTime), "create_time", endTime));
        if (CollectionUtils.isEmpty(orders)) {
            return adminOrderListVo;
        }
        List<Integer> collect = orders.stream().map(Orders::getOrderId).collect(Collectors.toList());
        List<OrderDetail> orderDetails = orderDetailService.selectList(new EntityWrapper<OrderDetail>().in("order_id", collect));
        if (CollectionUtils.isEmpty(orderDetails)) {
            return new AdminOrderListVo();
        }
        List<OrderDetail> goods = orderDetails.stream().filter(o -> !"盲盒".equals(o.getProductName())).collect(Collectors.toList());
        List<OrderDetail> box = orderDetails.stream().filter(o -> "盲盒".equals(o.getProductName())).collect(Collectors.toList());
        List<AdminOrderListVo.Box> boxList = new ArrayList<>();
        List<Integer> box_get = new ArrayList<>();
        box.stream().collect(Collectors.groupingBy(OrderDetail::getGoodsId)).forEach((k, v) -> {
            AdminOrderListVo.Box typebox = new AdminOrderListVo().new Box();
            StoreBlindBox storeBlindBox = storeBlindBoxService.selectById(k);
            typebox.setBox_name(storeBlindBox.getTitle());
            typebox.setBox_price(v.stream().map(OrderDetail::getProductPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            List<BigDecimal> box_before = new ArrayList<>();
            List<String> box_prize = new ArrayList<>();
            v.forEach(gs -> {
                if (!CollectionUtils.isEmpty(goods)) {
                    box_before.add(goods.stream().filter(g -> g.getOrderId().equals(gs.getOrderId())).map(OrderDetail::getProductPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                    box_prize.add(goods.stream().filter(g -> g.getOrderId().equals(gs.getOrderId())).map(OrderDetail::getProductName).collect(Collectors.joining(",")));
                }
            });
            if (!CollectionUtils.isEmpty(box_before)) {
                box_get.add(box_before.size());
                typebox.setBox_before(box_before.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            if (!CollectionUtils.isEmpty(box_prize)) {
                typebox.setBox_prize(box_prize.stream().collect(Collectors.joining(",")));
            } else {
                typebox.setBox_prize("均未开箱");
            }
            typebox.setOrder_sum(v.size());
            boxList.add(typebox);
        });
        adminOrderListVo.setUser_box_before(boxList.stream().map(AdminOrderListVo.Box::getBox_before).reduce(BigDecimal.ZERO, BigDecimal::add));
        adminOrderListVo.setOrder_sum(boxList.stream().mapToInt(AdminOrderListVo.Box::getOrder_sum).sum());
        adminOrderListVo.setUser_box_get(box_get.stream().reduce(Integer::sum).orElse(0));
        adminOrderListVo.setUser_box_pay(boxList.stream().map(AdminOrderListVo.Box::getBox_price).reduce(BigDecimal.ZERO, BigDecimal::add));
        adminOrderListVo.setBox_order(boxList);
        return adminOrderListVo;
    }

    /**
     * 获取用户订单列表
     * @param adminOrderListDto
     * @return
     */
    @Override
    public Page<AdminOrderBoxListVo> searchBoxOrder(AdminOrderListDto adminOrderListDto) {
        Integer currentPage = adminOrderListDto.getCurrentPage();
        if (currentPage!=null){
            Integer size = adminOrderListDto.getSize();
            adminOrderListDto.setCurrentPage((currentPage-1)*size);
        }
        if(adminOrderListDto.getOrderStatus()<0){
            adminOrderListDto.setOrderStatus(null);
        }
        Page<AdminOrderBoxListVo> adminOrderBoxListVoPage = new Page<>(adminOrderListDto.getCurrentPage(),adminOrderListDto.getSize());
        List<AdminOrderBoxListVo> adminOrderBoxListVos = new ArrayList<>();
        List<AdminOrderBoxListDto> listDtos= ordersMapper.searchBoxOrder(adminOrderListDto);
        listDtos.stream().forEach(o->{
            AdminOrderBoxListVo adminOrderBoxListVo = new AdminOrderBoxListVo();
            BeanUtils.copyProperties(o,adminOrderBoxListVo);
            adminOrderBoxListVos.add(adminOrderBoxListVo);
        });
        int count = this.selectCount(new EntityWrapper<Orders>().eq(StringUtils.isNotEmpty(adminOrderListDto.getUser_id()), "user_id", adminOrderListDto.getUser_id())
                .eq("store_order", 2)
                .eq("delete_flag", 0)
                .ge(StringUtils.isNotEmpty(adminOrderListDto.getStart_time()), "create_time", adminOrderListDto.getStart_time())
                .le(StringUtils.isNotEmpty(adminOrderListDto.getEnd_time()), "create_time", adminOrderListDto.getEnd_time()));
        adminOrderBoxListVoPage.setRecords(adminOrderBoxListVos).setTotal(count);


        return adminOrderBoxListVoPage;
    }

    /**
     * 获取子订单详细信息
     *
     * @param orderId
     * @return
     */
    @Override
    public List<AdminBoxOrderDetailVo> searchBoxOrderDetail(String orderId) {

        ArrayList<AdminBoxOrderDetailVo> adminBoxOrderDetailVos = new ArrayList<>();
        AdminBoxOrderDetailVo adminBoxOrderDetailVo = new AdminBoxOrderDetailVo();

//        1、获取订单详情   商品名称 奖品名称 稀有度

        Orders orders=this.selectOne(new EntityWrapper<Orders>().eq("order_id",orderId).eq("delete_flag", 0).eq("store_order", 2));
        if (BeanUtil.isEmpty(orders)){
            throw new BusinessException("400006","该订单不存在，或已删除");
        }
        adminBoxOrderDetailVo.setOrderTotal(orders.getProductAmountTotal().toString());
        //订单状态
        switch (orders.getOrderStatus()){
            case 1:
                adminBoxOrderDetailVo.setOrderStatus("待开箱");
                break;
            case 2:
                adminBoxOrderDetailVo.setOrderStatus("待提货");
                break;
            case 3:
                adminBoxOrderDetailVo.setOrderStatus("待发货");
                break;
            case 4:
                adminBoxOrderDetailVo.setOrderStatus("已发货");
                break;
        }
        // 商品名称 奖品名称
        List<OrderDetail> orderDetail = orderDetailService.selectList(new EntityWrapper<OrderDetail>().eq("order_id", orderId));
        if (BeanUtil.isEmpty(orderDetail)){
            throw new BusinessException("400006","该订单不存在，或已删除");
        }
        orderDetail.stream().forEach(o -> {
            if (o.getDeleteFlag()==0){
                if(o.getGoodsId()!=null&&o.getGoodsId()>0){
                    StoreBlindboxGoods storeBlindboxGoods = storeBlindboxGoodsService.selectById(o.getGoodsId());
                    adminBoxOrderDetailVo.setTag(storeBlindboxGoods.getTagText());
                }
                adminBoxOrderDetailVo.setGoodsName(o.getProductName());
            }
            if (o.getDeleteFlag()==2){
                adminBoxOrderDetailVo.setProductName(o.getProductName());
            }
        });
        adminBoxOrderDetailVos.add(adminBoxOrderDetailVo);

//        2、根据订单id去订单详情商品表查询


//        订单物流信息需不需要查


//        AdminOrderBoxVo adminOrderBoxVo = new AdminOrderBoxVo();
//        //获取订单信息
//        Orders orders=this.selectOne(new EntityWrapper<Orders>().eq("order_id",orderId).eq("delete_flag", 0).eq("store_order", 2));
//        if (BeanUtil.isEmpty(orders)){
//            throw new BusinessException("400006","该订单不存在，或已删除");
//        }
//        BeanUtil.copyProperties(orders,adminOrderBoxVo);
//        if (orders.getPayStatus()==1) {
//            PaymentChannel paymentChannel = paymentChannelService.selectOne(new EntityWrapper<PaymentChannel>().eq("CHANNEL_ID", orders.getPayChannel()));
//            PaymentMethod paymentMethod = paymentMethodService.selectOne(new EntityWrapper<PaymentMethod>().eq("CHANNEL_ID", paymentChannel.getChannelId()));
//            adminOrderBoxVo.setPayChannelName(paymentChannel.getChannelName());
//            adminOrderBoxVo.setPayName(paymentMethod.getPayName());
//        }
//        int delete_flag=2;
//        if (orders.getOrderStatus()==1){
//            delete_flag=0;
//        }
//        //获取盲盒订单详细信息
//        OrderDetail orderDetail = orderDetailService.selectOne(new EntityWrapper<OrderDetail>().eq("delete_flag", delete_flag).eq("order_id", orderId));
//        if (BeanUtil.isEmpty(orderDetail)){
//            throw new BusinessException("400007","无盲盒信息");
//        }
//        BeanUtil.copyProperties(orderDetail,adminOrderBoxVo);
//        //获取盲盒信息
//        StoreBlindBox storeBlindBox = storeBlindBoxService.selectOne(new EntityWrapper<StoreBlindBox>().eq("id", orderDetail.getGoodsId()));
        return adminBoxOrderDetailVos;
    }

    /**
     * 发货接口
     * @param deliveryVo
     * @return
     */
    @Override
    public Boolean changeOrderToSended(DeliveryVo deliveryVo) {
        //获取订单信息
        Orders orders=this.selectOne(new EntityWrapper<Orders>().eq("order_id",deliveryVo.getOrderId()).eq("delete_flag", 0).eq("store_order", 2));
        if (BeanUtil.isEmpty(orders)){
            throw new BusinessException("400008","该订单不存在，或已删除");
        }
        SysDictItem sysDictItem = sysDictItemService.selectById(deliveryVo.getExpressName());
        boolean b = this.updateById(new Orders().setOrderId(Integer.valueOf(deliveryVo.getOrderId())).setLogisticsFee(deliveryVo.getLogisticsFee()).setConsignTime(new Date()).setOrderStatus(4));
        boolean order_id = orderLogisticsService.update(new OrderLogistics().setExpressNo(deliveryVo.getExpressNo()).setLogisticsFee(deliveryVo.getLogisticsFee()).setLogisticsType(sysDictItem.getItemText()).setOrderlogisticsStatus("已发货"), new EntityWrapper<OrderLogistics>().eq("order_id", deliveryVo.getOrderId()));

        //增加代理金额 todo


        return b&order_id;
    }

    @Override
    public List<OrderRollListVo> orderRollList() {

        if (redisUtil.exists("rolllist")){
            return (List<OrderRollListVo>)redisUtil.get("rolllist");
        }else{
            List<OrderRollListVo> orderRollListVos = orderDetailMapper.orderRollList();
            orderRollListVos.stream().forEach(c->{
                long dateBetween = com.wenhui.core.base.utils.DateUtil.getDateBetween(c.getTime(), new Date());
                c.setSecond(String.format("%s秒前", dateBetween>600?RandomUtil.randomInt(500,600):dateBetween));

                c.setMobile(String.format("恭喜 %s 拿下 iPhone 14 Pro  ...", DataDesensitizedUtils.desensitizedPhoneNumber(c.getMobile()),
                        c.getName().length()>13?c.getName().substring(0,13):c.getName()));
                c.setTime(null);
            });
            redisUtil.set(
                    "rolllist",
                    orderRollListVos,
                    10*60);
            return orderRollListVos;
        }
    }

    /**
     * Yzf回调
     *
     * @param order
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderPaymentStatusYzf(Map<String, String> order) {
        //只有TRADE_SUCCESS是成功
        String tradeStatus = order.get("trade_status");
        if (StringUtils.isEmpty(tradeStatus)){
            log.info("yzf支付后的状态不是【TRADE_SUCCESS】，而是：{}",tradeStatus);
            return false;
        }
        try {
            if (tradeStatus.equals("TRADE_SUCCESS")){
                //商户订单号
                String outTradeNo = order.get("out_trade_no").toString();
                //根据订单号获取数据库订单信息
                Orders orders = ordersMapper.selectOne(new Orders().setOrderNo(outTradeNo).setDeleteFlag(0));
                if (BeanUtil.isEmpty(orders)){
                    log.info("根据订单号未获取到商户本身的订单");
                    return false;
//                    throw new BusinessException("90001","根据订单号未获取到商户本身的订单");
                }
                //获取签名与支付后的签名是否一致
                String rquestSign = redisUtil.get("yzf" + outTradeNo).toString();
                if (StringUtils.isEmpty(rquestSign)){
                    log.info("支付后的签名未获取到");
                    return false;
//                    throw new BusinessException("90002","支付后的签名未获取到");
                }
                String responseSign = order.get("sign").toString();
                if (!rquestSign.equals(responseSign)){
                    log.info("支付前和支付后签名不一致");
                    return false;
//                    throw new BusinessException("90003","支付前和支付后签名不一致");
                }
                //判断支付后的金额与所需要支付的金额是否一致
                //数据库的应付金额
                BigDecimal payableAmount = orders.getProductAmountTotal();
                //已付金额
                BigDecimal paidAmount = new BigDecimal(order.get("money"));
                //精度确认
                payableAmount = payableAmount.setScale(2, RoundingMode.HALF_UP);
                paidAmount = paidAmount.setScale(2, RoundingMode.HALF_UP);
                if(payableAmount.compareTo(paidAmount)!=0){
//                    throw new BusinessException("90004","支付前和支付后金额不一致");
                    log.info("支付前和支付后金额不一致");
                    return false;
                };
                //支付方订单号
                String tradeNo = order.get("trade_no");
                Orders orders1 = new Orders();
                orders1.setOutTradeNo(tradeNo);
                orders1.setOrderStatus(1);
                orders1.setPayStatus(1);
                orders1.setOrderAmountTotal(paidAmount);
                orders1.setPaymentTime(new Date());
                boolean update = this.update(orders1,new EntityWrapper<Orders>().eq("order_no",outTradeNo).eq("pay_status",0).eq("delete_flag",0));
                //TODO 参数为总利润
                RebatePart rebate = ordersRebateConfig.getRebate(orders1.getOrderAmountTotal());
                ordersRebateService.insert(new OrdersRebate(orders1.getUserId(), orders1.getOrderId(),orders1.getProductAmountTotal(),rebate.getUserRebate(),rebate.getUserRecommendRebate(),rebate.getTerraceRebate()));
                return true;
            }
        }catch (AssertionError assertionError) {
            log.error(assertionError.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ordersPayRequestLogService.insert(new OrdersPayRequestLog("/pay/status", JSONObject.toJSONString(order), 2));
        }
        return false;
    }

    /**
     * 支付宝支付成功回调，并校验签名是否一致
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alipayVerify(Map<String, String> params) {
        // 验证通过，根据商户订单号查询订单信息
        String outTradeNo = params.get("out_trade_no");
        Orders orders = ordersMapper.selectOne(new Orders().setOrderNo(outTradeNo).setPayStatus(0));
        if (orders == null) {
            // 订单不存在
            throw new BusinessException("80001","订单不存在");
        }
        PaymentChannel paymentChannel = paymentChannelService.selectOne(new EntityWrapper<PaymentChannel>().eq("CHANNEL_STATE", 0).eq("CHANNEL_ID", orders.getPayChannel()));
        // 调用SDK验证签名
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, paymentChannel.getPublicKey(),
                    "utf-8", "RSA2");
            if(signVerified){

                if (!params.get("total_amount").equals(orders.getProductAmountTotal().toString())) {
                    // 订单金额不一致
                    throw new BusinessException("80002","订单金额不一致");
                }

                if (!orders.getPayStatus().equals(0)) {
                    // 订单状态不是未支付
                    throw new BusinessException("80003","订单状态不是未支付");
                }
                // 更新订单状态，执行其他业务逻辑
                orders.setPayStatus(1);
                orders.setPaymentTime(new Date());
                orders.setOutTradeNo(params.get("trade_no"));
                orders.setOrderAmountTotal(new BigDecimal(params.get("total_amount")));
                ordersMapper.update(orders,new EntityWrapper<Orders>().eq("order_id",orders.getOrderId()));
                //TODO 参数为总利润
                RebatePart rebate = ordersRebateConfig.getRebate(orders.getOrderAmountTotal());
                ordersRebateService.insert(new OrdersRebate(orders.getUserId(), orders.getOrderId(),orders.getProductAmountTotal(),rebate.getUserRebate(),rebate.getUserRecommendRebate(),rebate.getTerraceRebate()));
                return true;
            }else {
            return false;
            }
        }catch (AlipayApiException e){
            System.out.println(e.getErrMsg());
            throw new BusinessException("80004",e.getMessage());
        }finally {
            ordersPayRequestLogService.insert(new OrdersPayRequestLog("/alipay/notify", JSONObject.toJSONString(params), 2));
        }
    }
}
