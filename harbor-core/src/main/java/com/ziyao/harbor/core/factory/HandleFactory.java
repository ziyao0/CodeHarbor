package com.ziyao.harbor.core.factory;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
public abstract class HandleFactory<T> {

    private Handler<T> handler;

    public void handle(T t) throws Exception {

        handler.handle(t);
        // 抛出异常执行下一个
        handler.checkedNextHandler(t);
    }

    /**
     * 初始化链表信息
     */
    protected abstract void init();
}
