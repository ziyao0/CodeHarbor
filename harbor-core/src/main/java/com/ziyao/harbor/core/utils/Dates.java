package com.ziyao.harbor.core.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class Dates {


    private Dates() {
    }

    /**
     * 判断是是否过期 误差为1分钟 一分钟之内都算过期
     *
     * @param expiresAt 时间类型
     */
    public static boolean isExpired(Date expiresAt) {
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 2);
        Date date = instance.getTime();
        return expiresAt.compareTo(date) <= 0;
    }

    /**
     * 判断是是否过期 误差为1分钟 一分钟之内都算过期
     *
     * @param expiresAt 时间类型
     */
    public static boolean isExpired(Date expiresAt, int amount) {
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, amount);
        Date date = instance.getTime();
        return expiresAt.compareTo(date) <= 0;
    }

    /**
     * 跳跃到指定的时间之后 （单位：分钟）
     *
     * @param minute 分钟数
     */
    public static Date skip(int minute) {
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, minute);
        return instance.getTime();
    }

}
