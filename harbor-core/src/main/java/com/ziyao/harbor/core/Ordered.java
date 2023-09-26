package com.ziyao.harbor.core;

/**
 * @author ziyao zhang
 * @since 2023/8/31
 */
@FunctionalInterface
public interface Ordered {
    /**
     * 最高优先级值的有用常量。
     *
     * @see java.lang.Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级值的有用常量。
     *
     * @see java.lang.Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 获取此对象的顺序值。
     * <p>
     * 较高的值被解释为较低的优先级。
     * <p>
     * 相同的顺序值将导致受影响对象的任意排序位置。
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    int getOrder();
}
