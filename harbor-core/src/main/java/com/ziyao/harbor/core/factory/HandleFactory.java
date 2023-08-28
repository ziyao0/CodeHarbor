package com.ziyao.harbor.core.factory;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
public abstract class HandleFactory<T> {

    private Handler<T> handler;

    public void handle(T t) {
        handler.handle(t);
        // 抛出异常执行下一个
        handler.checkedNextHandler(t);
    }

    protected abstract void init();
}
