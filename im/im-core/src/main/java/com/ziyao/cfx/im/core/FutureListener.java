package com.ziyao.cfx.im.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class FutureListener extends CompletableFuture<Boolean> implements Listener {

    private final Listener listener;

    private final AtomicBoolean started;

    public FutureListener(AtomicBoolean started) {
        this.started = started;
        this.listener = null;
    }

    public FutureListener(Listener listener, AtomicBoolean started) {
        this.listener = listener;
        this.started = started;
    }

    @Override
    public void success(Object... args) {
        if (isDone()) {
            return;
        } else {
            complete(started.get());
        }
        if (listener != null) {
            listener.success(args);
        }
    }

    @Override
    public void failure(Throwable cause) {
        if (isDone()) {
            return;
        } else {
            completeExceptionally(cause);
        }
        if (listener != null) {
            listener.failure(cause);
        } else {
            throw new RuntimeException(cause);
        }
    }

    /**
     * 系统监控 设置服务超时时间
     *
     * @param abstractStarter {@link this}
     */
    public void monitor(AbstractStarter abstractStarter) {
        if (!isDone()) {
            runAsync(() -> {
                try {
                    this.get(abstractStarter.timeoutMillis(), TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    this.failure(new RuntimeException("service %s monitor timeout " + abstractStarter.getClass().getSimpleName()));
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }
}
