package com.ziyao.harbor.core.lang;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public interface Mutable<T> {

    /**
     * 获得原始值
     * @return 原始值
     */
    T get();

    /**
     * 设置值
     * @param value 值
     */
    void set(T value);

}