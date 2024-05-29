package com.wenhui.project.biz.util;

import java.util.*;

/**
 * 不同概率抽奖工具包
 *
 * @author xinhe
 */
public class LotteryUtil {
    /**
     * 抽奖
     *
     * @param orignalRates
     *            原始的概率列表，保证顺序和实际物品对应
     * @return
     *         物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }

        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }

        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }



    public static void main(String[] args) {
        List<LotteryGift> lotteryGifts = new ArrayList<LotteryGift>();
        lotteryGifts.add(new LotteryGift(1, "P1", "智尊款", new Double(0)));
        lotteryGifts.add(new LotteryGift(2, "P2", "稀有款", new Double(0.4)));
        lotteryGifts.add(new LotteryGift(3, "P3", "普通款", new Double(0.6)));

        // 序号==物品Id==物品名称==概率
//        lotteryGifts.add(new LotteryGift(4, "P4", "物品4", 0.3d));
//        lotteryGifts.add(new LotteryGift(5, "P5", "物品5", 0d));
//        lotteryGifts.add(new LotteryGift(6, "P6", "物品6", -0.1d));
//        lotteryGifts.add(new LotteryGift(7, "P7", "物品7", 0.008d));

        List<Double> orignalRates = new ArrayList<Double>(lotteryGifts.size());
        for (LotteryGift lotteryGift : lotteryGifts) {
            double probability = lotteryGift.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }
        // statistics
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        double num = 1;
        for (int i = 0; i < num; i++) {
            int orignalIndex = LotteryUtil.lottery(orignalRates);

            Integer value = count.get(orignalIndex);
            count.put(orignalIndex, value == null ? 1 : value + 1);
        }

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(lotteryGifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability=" + entry.getValue() / num);
        }
    }
}
