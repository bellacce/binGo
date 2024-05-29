package com.wenhui.project.biz.util;

import org.apache.commons.lang3.RandomUtils;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author
 */
public class IDGenerator {
    private final static long BASE = 531055996252L;//减除数
    private static int serverId = 2; //2字节

    private static AtomicLong atoLong = new AtomicLong();

    public static Long next() {
        long t = (System.currentTimeMillis() - BASE);
        int random = RandomUtils.nextInt(); // .nextInt(31);//随机数
        int lowValue = ((serverId << 5) | (random & 0x1f));
        lowValue = (lowValue & 0x7f);

        BigInteger ret = ((BigInteger.valueOf(t).shiftLeft(7)).or(BigInteger.valueOf(lowValue)));
        return ret.longValue();
    }

    /**
     * 消息发送场景需要的消息ID
     *
     * @return
     */
    public static Long nextMid() {
        Long mid = atoLong.incrementAndGet();
        if (mid >= Integer.MAX_VALUE) {
            atoLong.set(1L);
        }
        return mid;
    }
}