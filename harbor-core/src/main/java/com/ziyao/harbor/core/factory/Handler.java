package com.ziyao.harbor.core.factory;

/**
 * @author ziyao zhang
 * @since 2023/8/28
 */
public abstract class Handler<T> {

    private Handler<T> nextHandler;

    /**
     * 关联链表信息
     *
     * @param nextHandler 节点数据
     */
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

    /**
     * 检查链表是否存在下一个节点，如果存在则执行不存在链表执行完成
     *
     * @param t 处理对象
     * @throws Exception 异常信息
     */
    public void checkedNextHandler(T t) throws Exception {
        if (null != nextHandler) {
            nextHandler.handle(t);
        }
    }

    /**
     * 继承处理器实现该方法
     * 该方法抛出异常，执行链路中断
     *
     * @param t 入参对象
     * @throws Exception 处理异常信息
     */
    protected abstract void handle(T t) throws Exception;


}
