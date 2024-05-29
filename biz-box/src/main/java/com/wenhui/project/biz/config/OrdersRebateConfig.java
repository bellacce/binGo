package com.wenhui.project.biz.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 订单利润配置
 @create 2023/2/22
*/
@Component
public class OrdersRebateConfig implements InitializingBean {
    //order:
    //  rebate:
    //    userRebate: 0.1
    //    userRecommendRebate: 0.2
    //    terraceRebate: 0.7
    /**
     * 用户抽成
     */
    @Value("${order.rebate.userRebate}")
    private double userRebate;
    /**
     * 上级用户抽成
     */
    @Value("${order.rebate.userRecommendRebate}")
    private double userRecommendRebate;
    /**
     * 平台抽成
     */
    @Value("${order.rebate.terraceRebate}")
    private double terraceRebate;


    public static BigDecimal USER_REBATE;
    public static BigDecimal USER_RECOMMEND_REBATE;
    public static BigDecimal TERRACE_REBATE;

    @Override
    public void afterPropertiesSet() throws Exception {
        USER_REBATE = new BigDecimal(userRebate);
        USER_RECOMMEND_REBATE = new BigDecimal(userRecommendRebate);
        TERRACE_REBATE = new BigDecimal(terraceRebate);
    }


    public RebatePart getRebate(BigDecimal rebate) {
        BigDecimal num1 = new BigDecimal("0.00");
        BigDecimal num2 = new BigDecimal("0.00");
        BigDecimal num3 = new BigDecimal("0.00");

        //加减乘除操作，精度取小数点后两位
        num1 = rebate.multiply(USER_REBATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        num2 = rebate.multiply(USER_RECOMMEND_REBATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        num3 = rebate.multiply(TERRACE_REBATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        return new RebatePart(num1, num2, num3);
    }

    public static Map<String, String> sysPrint(){
        Map<String, String> map = new HashMap<>();
        map.put("name","OrdersRebateConfig");
        map.put("USER_REBATE",USER_REBATE.toString());
        map.put("USER_RECOMMEND_REBATE",USER_RECOMMEND_REBATE.toString());
        map.put("TERRACE_REBATE",TERRACE_REBATE.toString());
        return map;
    }
}
