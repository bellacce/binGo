package com.wenhui.project.biz.util;

import cn.hutool.core.util.RandomUtil;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OpenBoxUtil {

    public static OpenBox openBox(List<OpenBox> openBoxes) {
        List<OpenBox> sorted = openBoxes.stream().sorted(Comparator.comparing(OpenBox::getProbability)).collect(Collectors.toList());

        // 设置四个概率变量
        double percent1 = sorted.get(0).getProbability();
        double percent2 = sorted.get(1).getProbability();
        double percent3 = sorted.get(2).getProbability();
        double percent4 = sorted.get(3).getProbability();

// 将概率值乘以1000000，转化为整数
        int intPercent1 = (int) (percent1 * 1000000);
        int intPercent2 = (int) (percent2 * 1000000);
        int intPercent3 = (int) (percent3 * 1000000);
        int intPercent4 = 1000000000 - intPercent1 - intPercent2 - intPercent3;
// 生成一个0到999999999之间的随机整数
        int rand = (int) (Math.random() * 1000000000);

        // 根据概率区间判断抽中的物品
        if (rand < intPercent1) {
            return openBoxes.get(0);
        } else if (rand < intPercent1 + intPercent2) {
            return openBoxes.get(1);
        } else if (rand < intPercent1 + intPercent2 + intPercent3) {
            return openBoxes.get(2);
        } else {
            return sorted.get(3);
        }
    }

    public static void verfypercent(double percent1,double percent2,double percent3,double percent4) {
// 将概率值乘以1000000，转化为整数
        int intPercent1 = (int) (percent1 * 1000000);
        int intPercent2 = (int) (percent2 * 1000000);
        int intPercent3 = (int) (percent3 * 1000000);
        int intPercent4 = (int) (percent4 * 1000000);
//        int intPercent4 = 1000000000 - intPercent1 - intPercent2 - intPercent3;

// 检查概率之和是否为100%
        if (intPercent1 + intPercent2 + intPercent3 + intPercent4 != 100000000) {
            throw new BusinessException(String.valueOf(ErrorCode.SERVER_ERROR),"概率之和不等于100%，请重新设置");
        }
    }

    public static void main(String[] args){
        List<OpenBox> openBoxes = new ArrayList<>();
        openBoxes.add(new OpenBox("传说商品", 30));
        openBoxes.add(new OpenBox("史诗商品", 30));
        openBoxes.add(new OpenBox("稀有商品", 20));
        openBoxes.add(new OpenBox("高级商品", 20));
        int num0 = 0;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        for (int i = 0; i < 1000; i++) {
            OpenBox openBox = openBox(openBoxes);
            if ("传说商品".equals(openBox.getName())){
                num0++;
                continue;
            }
            if ("史诗商品".equals(openBox.getName())){
                num1++;
                continue;
            }
            if ("稀有商品".equals(openBox.getName())){
                num2++;
                continue;
            }
            if ("高级商品".equals(openBox.getName())){
                num3++;
                continue;
            }
        }

        System.out.println("传说商品"+num0);
        System.out.println("史诗商品"+num1);
        System.out.println("稀有商品"+num2);
        System.out.println("高级商品"+num3);

    }
}