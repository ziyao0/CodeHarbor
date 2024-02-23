package com.ziyao.harbor.core.factory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
@Setter
@Getter
public abstract class AbstractFactory<T> {

    private AbstractHandler<T> abstractHandler;

    public void handle(T t) {

        abstractHandler.handle(t);
        // 抛出异常执行下一个
        abstractHandler.checkedNextHandler(t);
    }

    /**
     * 初始化链表信息
     */
    protected abstract void init();

}
