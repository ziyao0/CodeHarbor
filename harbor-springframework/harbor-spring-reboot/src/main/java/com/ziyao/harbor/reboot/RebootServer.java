package com.ziyao.harbor.reboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public class RebootServer implements ApplicationContextAware {

    /**
     * true 正在重启
     */
    private static final AtomicBoolean restarted = new AtomicBoolean(false);
    private ConfigurableApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.context = (ConfigurableApplicationContext) context;
    }

    public void reboot() {
        if (restarted.compareAndSet(false, true)) {
            // 执行重启
            doReboot();
        } else {
            System.out.println("Restarting.");
        }
    }

    private void doReboot() {
        try {
            System.out.println("Restart application start.");
            // 执行关机
            shutdown();
            // RebootServer 关闭时是异步执行, 此处等待 20 秒后再往下执行,
            // 防止关闭 JVM 过快导致 Spring 应用程序上下文没有优雅关闭的问题.
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException ignore) {
            // ignore.
        } finally {
            Runtimes.asyncExit(0, 60);
            System.out.println("Restart application end.");
        }
    }

    /**
     * 关闭应用程序上下文，释放实现可能持有的所有资源和锁。这包括销毁所有缓存的单例 bean。
     */
    public void shutdown() {
        if (this.context == null) {
            System.out.println("No context to shutdown.");
            return;
        }
        try {
            System.out.println("Shutting down");
        } finally {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                context.close();
            });
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }
}
