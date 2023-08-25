package com.ziyao.harbor.core;

/**
 * 注入器
 *
 * @author ziyao zhang
 * @since 2023/8/22
 */
public interface Injector<T, R> {

    /**
     * 对给定参数R,注入T的相关信息
     *
     * @param r 目标注入对象
     * @param t 要注入的信息
     * @return 返回目标对象
     */
    R inject(R r, T t);
}
