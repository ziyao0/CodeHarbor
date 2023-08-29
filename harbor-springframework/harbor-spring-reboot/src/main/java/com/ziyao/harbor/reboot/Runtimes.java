package com.ziyao.harbor.reboot;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public abstract class Runtimes {

    /**
     * 是否正在退出.
     */
    private static final AtomicBoolean EXITING_CLOSE = new AtomicBoolean(false);

    private Runtimes() {
    }

    /**
     * 在新的线程中终止当前 Java 虚拟机.
     *
     * @param status           退出状态码, 0 表示正常退出, 其他表示非正常退出
     * @param softExitDuration 软退出限制时间, 单位秒. 超过该时间限制后将调用 {@link Runtime#halt(int)} 强制退出虚拟机
     * @see #exit(int, int)
     */
    public static void asyncExit(int status, int softExitDuration) {
        Thread thread = new Thread(() -> exit(status, softExitDuration));
        thread.setContextClassLoader(Runtimes.class.getClassLoader());
        thread.start();
    }

    /**
     * 终止当前 Java 虚拟机, 相比于 {@link System#exit(int)} 方法, 该方法会首先调用 {@link Runtime#exit(int)} 按照正常流程退出 JVM，
     * 如果指定时间后 JVM 依然未退出(一般是 shutdown hook 未执行完成)则调用 {@link Runtime#halt(int)} 方法强制退出 JVM.
     *
     * @param status           退出状态码, 0 表示正常退出, 其他表示非正常退出
     * @param softExitDuration 软退出限制时间, 单位秒. 超过该时间限制后将调用 {@link Runtime#halt(int)} 强制退出虚拟机
     */
    public static void exit(int status, int softExitDuration) {
        if (!EXITING_CLOSE.compareAndSet(false, true)) {
            return;
        }
        if (softExitDuration <= 0) {
            throw new IllegalArgumentException("softExitDuration must be greater than 0");
        }
        // 强制退出时间, 超过该时间将调用 Runtime#halt() 方法强制退出 JVM.
        long forcedExitTime = System.currentTimeMillis() + softExitDuration * 1000L;
        Thread exitWatchdog = new Thread(() -> {
            while (true) {
                long time = System.currentTimeMillis();
                if (time < forcedExitTime) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignore) {
                        // ignore.
                    }
                } else {
                    Runtime.getRuntime().halt(status);
                }
            }
        });
        exitWatchdog.start();
        System.exit(status);
    }
}
