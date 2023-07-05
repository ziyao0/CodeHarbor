package com.ziyao.cfx.mpusher.core;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public interface DispatchHolder {


    /**
     * Implement this method to dynamically add a handler
     */
    default void addLast() {
    }
}
