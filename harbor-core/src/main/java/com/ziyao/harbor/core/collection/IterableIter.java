package com.ziyao.harbor.core.collection;

import com.ziyao.harbor.core.lang.NonNull;

import java.util.Iterator;

/**
 * 提供合成接口，共同提供{@link Iterable}和{@link Iterator}功能
 *
 * @param <T> 节点类型
 * @author ziyao zhang
 * @since 2023/10/19
 */
public interface IterableIter<T> extends Iterable<T>, Iterator<T> {

    @NonNull
    @Override
    default Iterator<T> iterator() {
        return this;
    }
}
