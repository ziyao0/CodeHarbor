package com.ziyao.harbor.core;

/**
 * 提取器
 *
 * @author ziyao zhang
 * @since 2023/8/22
 */
@FunctionalInterface
public interface Extractor<T, R> {

    /**
     * 从给定信息T中提取相关信息，填充到R并返回
     *
     * @param t 给定相关信息
     * @return <code>R</code>返回R
     */
    R extract(T t);
}
