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

    public static void start(final String taskId) {
        StopWatchInterior.INSTANCE.start(taskId);
    }

    public static void stop(final String taskId) {
        StopWatchInterior.INSTANCE.stop(taskId);
    }

    public static void stop() {
        StopWatchInterior.INSTANCE.stop();
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
            StopWatch stopWatch = stopWatchThreadLocal.get();
            if (stopWatch != null) {
                LOGGER.info(stopWatch.prettyPrint());
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
        Thread.sleep(2000);
        StopWatches.start("task14");
        StopWatches.start("task15");
        StopWatches.start("task16");
        StopWatches.stop();
        System.out.println(StopWatches.prettyPrint());

        new Thread(new Runnable() {
            @Override
            public void run() {
                StopWatches.enabled();
                StopWatches.start("task1");
                StopWatches.start("task2");
                StopWatches.start("task3");
                StopWatches.start("task4");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                StopWatches.start("task5");
                StopWatches.start("task6");
                StopWatches.start("task7");
                StopWatches.start("task8");
                StopWatches.start("task9");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                StopWatches.start("task10");
                StopWatches.start("task11");
                StopWatches.start("task12");
                StopWatches.start("task13");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                StopWatches.start("task14");
                StopWatches.start("task15");
                StopWatches.start("task16");
                StopWatches.stop();
                System.out.println(StopWatches.prettyPrint());
            }
        }).start();
    }
}
