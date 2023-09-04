package com.ziyao.harbor.core;

/**
 * 注入器
 *
 * @author ziyao zhang
 * @since 2023/8/22
 */
public interface Injector<T, V> {


    /**
     * 对给定参数R,注入T的相关信息
     *
     * @param t 要注入的信息
     * @param v 目标注入对象
     */
    void inject(T t, V v);
}
