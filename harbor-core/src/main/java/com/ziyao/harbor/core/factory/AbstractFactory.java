package com.ziyao.harbor.core.factory;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
@Getter
public abstract class AbstractFactory<T> {

    private AbstractHandler<T> abstractHandler;

    public void handle(T t) throws Exception {

        abstractHandler.handle(t);
        // 抛出异常执行下一个
        abstractHandler.checkedNextHandler(t);
    }

    /**
     * 初始化链表信息
     */
    protected abstract void init();

    public void setAbstractHandler(AbstractHandler<T> abstractHandler) {
        this.abstractHandler = abstractHandler;
    }
}
