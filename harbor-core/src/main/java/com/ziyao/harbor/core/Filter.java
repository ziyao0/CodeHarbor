package com.ziyao.harbor.core;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
@FunctionalInterface
public interface Filter<T> {
    /**
     * 是否接受对象
     *
     * @param t 检查的对象
     * @return 是否接受对象
     */
    boolean accept(T t);
}
