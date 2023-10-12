package com.ziyao.harbor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziyao zhang
 * @since 2023/10/11
 */
public abstract class StopWatches {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopWatches.class);
    private static final ThreadLocal<StopWatch> STOP_WATCH_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<AtomicBoolean> ATOMIC_BOOLEAN_THREAD_LOCAL = new ThreadLocal<>();

    public static void start(final String taskId) {
        if (isEnabled()) {
            StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
            if (null == stopWatch) {
                stopWatch = new StopWatch();
                STOP_WATCH_THREAD_LOCAL.set(stopWatch);
            }
            stopWatch.start(taskId);
        }
    }

    public static void stop(final String taskId) {
        if (isEnabled()) {
            StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
            if (stopWatch == null) {
                LOGGER.error("任务 " + taskId + " 没有正在执行！");
            } else {
                stopWatch.stop(taskId);
            }
        }
    }

    public static void stop() {
        if (isEnabled()) {
            StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
            if (stopWatch == null) {
                LOGGER.error("没有执行的任务");
            } else {
                stopWatch.stop();
            }
        }
    }

    public static void consolePrettyPrint() {
        StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (stopWatch != null) {
            LOGGER.info(stopWatch.prettyPrint());
        }
    }

    public static String prettyPrint() {
        StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (stopWatch != null) {
            return stopWatch.prettyPrint();
        }
        return null;
    }

    public static void enabled() {
        if (!isEnabled()) {
            AtomicBoolean enabled = ATOMIC_BOOLEAN_THREAD_LOCAL.get();
            if (null == enabled) {
                enabled = new AtomicBoolean();
            }
            enabled.compareAndSet(false, true);
            ATOMIC_BOOLEAN_THREAD_LOCAL.set(enabled);
        }
    }

    public static void disabled() {
        if (isEnabled()) {
            AtomicBoolean enabled = ATOMIC_BOOLEAN_THREAD_LOCAL.get();
            if (enabled != null) {
                enabled.compareAndSet(true, false);
            }
        }
    }

    private static boolean isEnabled() {
        AtomicBoolean enabled = ATOMIC_BOOLEAN_THREAD_LOCAL.get();
        if (null == enabled) {
            return false;
        }
        return enabled.get();
    }

    private StopWatches() {
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatches.enabled();
        StopWatches.start("task1");
        StopWatches.start("task2");
        StopWatches.start("task3");
        StopWatches.start("task4");
        Thread.sleep(1000);
        StopWatches.start("task5");
        StopWatches.start("task6");
        StopWatches.start("task7");
        StopWatches.start("task8");
        StopWatches.start("task9");
        Thread.sleep(1000);
        StopWatches.start("task10");
        StopWatches.start("task11");
        StopWatches.start("task12");
        StopWatches.start("task13");
        Thread.sleep(20000);
        StopWatches.start("task14");
        StopWatches.start("task15");
        StopWatches.start("task16");
        StopWatches.stop();
        System.out.println(StopWatches.prettyPrint());
    }
}
