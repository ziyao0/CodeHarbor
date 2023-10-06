package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2023/9/27
 */
public abstract class GatewayStopWatches {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayStopWatches.class);

    private static final String STOP_WATCH_ID = "gateway_stopwatch";
    private static final String STOP_WATCH_ENABLED = "gateway_stopwatch_enabled";

    /**
     * 开始记录任务执行信息
     *
     * @param taskId   任务id
     * @param exchange 用于 HTTP 请求-响应交互
     */
    public static void start(final String taskId, ServerWebExchange exchange) {
        if (isEnabled(exchange)) {
            StopWatch stopWatch = exchange.getAttribute(STOP_WATCH_ID);
            if (stopWatch == null) {
                stopWatch = new StopWatch(STOP_WATCH_ID);
                exchange.getAttributes().put(STOP_WATCH_ID, stopWatch);
            }
            stopWatch.start(taskId);
        }
    }

    /**
     * 停止当前记录的任务
     *
     * @param taskId   任务id
     * @param exchange 用于 HTTP 请求-响应交互
     */
    public static void stop(final String taskId, ServerWebExchange exchange) {
        if (isEnabled(exchange)) {
            StopWatch stopWatch = getStopWatch(exchange);
            if (stopWatch == null) {
                throw new IllegalArgumentException("当前没有任务正在执行！");
            }
            stopWatch.stop(taskId);
        }
    }

    /**
     * 获取记录任务执行时间的秒表
     *
     * @param exchange 用于 HTTP 请求-响应交互
     * @return {@link StopWatch}
     */
    public static StopWatch getStopWatch(ServerWebExchange exchange) {
        if (isEnabled(exchange))
            return exchange.getAttribute(STOP_WATCH_ID);
        else
            return null;
    }

    public static boolean isEnabled(ServerWebExchange exchange) {
        Boolean enabled = exchange.getAttribute(STOP_WATCH_ENABLED);
        if (enabled == null) {
            enabled = LOGGER.isDebugEnabled();
            exchange.getAttributes().put(STOP_WATCH_ENABLED, true);
        }
        return true;
    }

    private GatewayStopWatches() {
    }
}
