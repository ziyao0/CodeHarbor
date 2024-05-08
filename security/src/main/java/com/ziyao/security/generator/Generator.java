package com.ziyao.security.generator;

/**
 * @author ziyao
 * @since 2024/05/08 15:10:43
 */
@FunctionalInterface
public interface Generator<T, V> {

    V generate();

    default V generate(T context) {
        return generate();
    }
}
