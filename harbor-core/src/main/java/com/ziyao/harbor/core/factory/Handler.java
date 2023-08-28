package com.ziyao.harbor.core.factory;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
public abstract class Handler<T> {

    private Handler<T> nextHandler;

    public void linkWith(Handler<T> nextHandler) {
        if (this.nextHandler == null) {
            this.nextHandler = nextHandler;
            return;
        }
        Handler<T> lastHandler = this.nextHandler;
        while (lastHandler.nextHandler != null) {
            lastHandler = lastHandler.nextHandler;
        }
        lastHandler.nextHandler = nextHandler;
    }

    protected abstract void handle(T t);

    public void checkedNextHandler(T t) {
        if (null != nextHandler) {
            nextHandler.handle(t);
        }
    }
}
