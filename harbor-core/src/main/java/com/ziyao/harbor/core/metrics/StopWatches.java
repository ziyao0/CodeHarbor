package com.ziyao.harbor.core.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziyao zhang
 * @since 2023/10/11
 */
public abstract class StopWatches {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopWatches.class);

    public static void start(final String taskId) {
        StopWatchInterior.INSTANCE.start(taskId);
    }

    public static void stop(final String taskId) {
        StopWatchInterior.INSTANCE.stop(taskId);
    }

    public static void stop() {
        StopWatchInterior.INSTANCE.stop();
    }

    public static void consolePrettyPrintOfLoggerOrOut() {
        StopWatchInterior.INSTANCE.consolePrettyPrintOfLoggerOrOut();
    }

    public static void consolePrettyPrint() {
        StopWatchInterior.INSTANCE.consolePrettyPrint();
    }

    public static String prettyPrint() {
        return StopWatchInterior.INSTANCE.prettyPrint();
    }

    public static void enabled() {
        StopWatchInterior.INSTANCE.enabled();
    }

    public static void enabled(String taskName) {
        StopWatchInterior.INSTANCE.enabled(taskName);
    }

    public static void disabled() {
        StopWatchInterior.INSTANCE.disabled();
    }

    private static boolean isEnabled() {
        return StopWatchInterior.INSTANCE.isEnabled();
    }

    private StopWatches() {
        throw new IllegalArgumentException();
    }

    private enum StopWatchInterior {
        INSTANCE;

        StopWatchInterior() {

        }

        private final ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();
        private final ThreadLocal<AtomicBoolean> enabledThreadLocal = new ThreadLocal<>();

        public void start(final String taskId) {
            if (isEnabled()) {
                StopWatch stopWatch = stopWatchThreadLocal.get();
                if (null == stopWatch) {
                    stopWatch = new StopWatch();
                    stopWatchThreadLocal.set(stopWatch);
                }
                stopWatch.start(taskId);
            }
        }

        public void stop(final String taskId) {
            if (isEnabled()) {
                StopWatch stopWatch = stopWatchThreadLocal.get();
                if (stopWatch == null) {
                    LOGGER.error("任务 " + taskId + " 没有正在执行！");
                } else {
                    stopWatch.stop(taskId);
                }
            }
        }

        public void stop() {
            if (isEnabled()) {
                StopWatch stopWatch = stopWatchThreadLocal.get();
                if (stopWatch == null) {
                    LOGGER.error("没有执行的任务");
                } else {
                    stopWatch.stop();
                }
            }
        }

        public void consolePrettyPrint() {
            String prettyPrint = prettyPrint();

            if (prettyPrint != null) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prettyPrint);
                } else {
                    LOGGER.info("打印结果失败，当前日志级别为{},需要设置为DEBUG模式完成打印",
                            LOGGER.isInfoEnabled()
                                    ? Level.INFO.toString() : LOGGER.isErrorEnabled()
                                    ? Level.ERROR.toString() : LOGGER.isErrorEnabled()
                                    ? Level.WARN.toString() : "未知日志级别类型"
                    );
                }
            }
        }

        public void consolePrettyPrintOfLoggerOrOut() {
            String prettyPrint = prettyPrint();

            if (prettyPrint != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prettyPrint);
                } else {
                    System.out.println(prettyPrint);
                }
            }
        }

        public String prettyPrint() {
            StopWatch stopWatch = stopWatchThreadLocal.get();
            if (stopWatch != null) {
                return stopWatch.prettyPrint();
            }
            return null;
        }

        public void enabled() {
            if (!isEnabled()) {
                AtomicBoolean enabled = enabledThreadLocal.get();
                if (null == enabled) {
                    enabled = new AtomicBoolean();
                }
                enabled.compareAndSet(false, true);
                enabledThreadLocal.set(enabled);
            }
        }

        public void enabled(String taskName) {
            if (!isEnabled()) {
                AtomicBoolean enabled = enabledThreadLocal.get();
                if (null == enabled) {
                    enabled = new AtomicBoolean();
                }
                enabled.compareAndSet(false, true);
                enabledThreadLocal.set(enabled);
                StopWatch stopWatch = new StopWatch(taskName);
                stopWatchThreadLocal.set(stopWatch);
            }
        }

        public void disabled() {
            if (isEnabled()) {
                AtomicBoolean enabled = enabledThreadLocal.get();
                if (enabled != null) {
                    enabled.compareAndSet(true, false);
                    stopWatchThreadLocal.remove();
                    enabledThreadLocal.remove();
                }
            }
        }

        private boolean isEnabled() {
            AtomicBoolean enabled = enabledThreadLocal.get();
            if (null == enabled) {
                return false;
            }
            return enabled.get();
        }
    }
}
