package com.ziyao.harbor.core;

/**
 * 校验器
 *
 * @author ziyao zhang
 * @since 2023/8/22
 */
public interface Validator<T> {

    /**
     * 对给定参数进行校验
     *
     * @param t 需要校验的参数对象
     */
    void validate(T t);
}
