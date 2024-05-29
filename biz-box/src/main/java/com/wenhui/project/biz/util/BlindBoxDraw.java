package com.wenhui.project.biz.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 盲盒开盒核心算法
 * 需求说明：盲盒id为19(19.手机,20.手办等)中有四种商品类型(1.至尊,2.超稀有,3.稀有,4.普通),至尊抽中的概率为0.02%，超稀有抽中的概率为0.06%，稀有抽中的概率为3.2%，普通抽中的概率为96%;
 *
 * @author JustJavaIt
 * @date 2022/01/29 10:47
 */
public class BlindBoxDraw {
    public static void main(String[] args) {

        //模拟数据，根据用户抽的盲盒id=19(前端传)，查询对应盲盒id下商品类型((1.至尊,2.超稀有,3.稀有,4.普通))的概率
        List<BlindBoxRule> blindBoxRules = BlindBoxRule.queryByBoxId();

        //将商品概率添加到集合中,用于下面抽奖
        List<Integer> probList = new ArrayList<>(blindBoxRules.size());
        for (BlindBoxRule rule : blindBoxRules) {
            probList.add(rule.getRealRate());
        }

        //开盒核心方法
        int drawResult = draw(probList);
        System.out.println("draw返回值为:" + drawResult);

        //注意List下标从0开始
        switch (probList.get(drawResult)) {
            case 200:
                System.out.println("恭喜你抽中至尊款商品");
                return;
            case 600:
                System.out.println("恭喜你抽中超稀有款商品");
                return;
            case 3200:
                System.out.println("恭喜你抽中稀有商品");
                return;
            case 96000:
                System.out.println("恭喜你抽中普通款商品");
                return;
            default:
                System.out.println("异常");
        }

        //实际业务中根据命中的商品类型((1.至尊,2.超稀有,3.稀有,4.普通))获得该类型下对应的商品(手机，平板等),每个商品也有一个权重值,也是通过类似上面的draw()抽取商品后，再判断用户是否符合一些别的门槛，符合的话就算开箱完成。
    }

    /**
     * 大致流程说明：计算出的sortRateList里面的概率有四个,0.0002,0.0006,0.032,0.96,随机生成的浮点数在0-1内，假如随机数为0.2546，
     * 那么sortRateList排序后为0.0002,0.0006,0.032,0.2546,0.96,那么返回的索引sortRateList.indexOf(random)就是4，既抽中的类型为普通款
     *
     * @param probList
     * @return
     */
    public static int draw(List<Integer> probList) {
        //最终加入随机值后排序的集合
        List<Double> sortRateList = new ArrayList<>();

        // 计算概率总和
        Integer sumRate = 0;
        for (Integer prob : probList) {
            sumRate += prob;
        }

        if (sumRate != 0) {
            // 概率所占比例
            double rate = 0D;
            for (Integer prob : probList) {
                rate += prob;
                // 构建一个比例区段组成的集合(避免概率和不为1)
                sortRateList.add(rate / sumRate);
            }

            // 随机生成一个随机数，并排序
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            //生成0-1之间的Double类型随机数 eg：0.7570711......
            double random = threadLocalRandom.nextDouble(0, 1);
            System.out.println("生成的随机数值为:" + random);
            sortRateList.add(random);
            Collections.sort(sortRateList);
            System.out.println("排序后的sortRateList为:" + sortRateList.toString());

            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }

        return -1;
    }
}