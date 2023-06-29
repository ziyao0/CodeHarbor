package com.ziyao.cfx.mpusher.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public abstract class AbstractStarter implements Starter {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(AbstractStarter.class);

    protected final AtomicBoolean started = new AtomicBoolean();

    private static final AtomicInteger MISSIONS_RETRIED = new AtomicInteger(0);

    private final NioEventLoopGroup workGroup;
    private NioEventLoopGroup bossGroup;

    private ServerBootstrap serverBootstrap;

    private Bootstrap bootstrap;


    public AbstractStarter(ServerBootstrap serverBootstrap, NioEventLoopGroup bossGroup) {
        this.serverBootstrap = serverBootstrap;
        this.workGroup = new NioEventLoopGroup();
        this.bossGroup = bossGroup;
    }

    public AbstractStarter(Bootstrap bootstrap) {
        this.workGroup = new NioEventLoopGroup();
        this.bootstrap = bootstrap;
    }

    /**
     * Start the netty service
     *
     * @param port The port number
     */
    @Override
    public void start(final int port) {
        init();
        LOGGER.info("netty core args Initialization complete!");
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        LOGGER.info("netty server is starting ...");
        final ChannelFuture future = this.serverBootstrap.bind(port);
        future.addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                LOGGER.info("Netty Server is started success!");
                LOGGER.info("Netty Server port:{}", port);
            } else {
                LOGGER.error("Netty Server is started fail!");
            }
        });
    }

    @Override
    public void start(String host, int port) {
        init();
        ChannelFuture future = bootstrap.connect(host, port);
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                LOGGER.info("Netty Client SUCCESS!");
                LOGGER.info("Netty Server Address：{}:{}!", host, port);
            } else {
                LOGGER.error("Netty Client FAIL!");
            }
        });
    }

    @Override
    public void start(URI uri) throws InterruptedException {
        init();
        ChannelFuture channelFuture = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                LOGGER.info("Netty Server Address：{}:{}{}!", uri.getHost(), uri.getPort(), uri.getPath());
                MISSIONS_RETRIED.set(0);
                LOGGER.info("Netty Client Shake Hands SUCCESS! Reset retries!");
            } else {
                future.channel().eventLoop().schedule(() -> {
                    try {
                        int retryCount = MISSIONS_RETRIED.incrementAndGet();
                        LOGGER.error("The client reconnects! the number of retries is:{}", retryCount);
                        start(uri);
                    } catch (Exception e) {
                        LOGGER.error("Reconnect abnormal!,error:{}", e.getMessage());
                    }
                }, getMissionsRetried(), TimeUnit.MILLISECONDS);
                LOGGER.error("Netty Client Shake Hands FAIL!");
            }
        });
    }

    /**
     * 降级获取连接重试时间
     *
     * @return 返回重试时间
     */
    private static long getMissionsRetried() {
        // Math.
        if (MISSIONS_RETRIED.get() <= 3) {
            return 5000;
        } else if (MISSIONS_RETRIED.get() <= 6) {
            return 30000;
        } else {
            return 60000;
        }
    }

    @Override
    public void close() {
        if (isRunning()) {
            // Close resource ,Uninterrupted sync off
            if (Objects.nonNull(bossGroup)) {
                bossGroup.shutdownGracefully().syncUninterruptibly();
            }
            workGroup.shutdownGracefully().syncUninterruptibly();
        }
    }

    @Override
    public boolean isRunning() {
        return started.get();
    }


    /**
     * try start netty server.
     *
     * @param listener   {@link Listener}
     * @param functionEx this function
     */
    protected void tryStart(Listener listener, FunctionEx functionEx) {
        FutureListener wrapListener = wrap(listener);
        if (started.compareAndSet(false, true)) {
            try {
                init();
                functionEx.apply(listener);
                wrapListener.monitor(this);
            } catch (Throwable e) {
                wrapListener.failure(e);
                throw new RuntimeException(e);
            }
        } else {
            if (throwIfStarted()) {
                wrapListener.failure(new RuntimeException("Prevent Listener from being executed repeatedly"));
            } else {
                wrapListener.success();
            }
        }
    }

    /**
     * 控制当服务已经启动后，重复调用start方法，是否抛出服务已经启动异常
     * 默认是true
     *
     * @return true:抛出异常
     */
    protected boolean throwIfStarted() {
        return true;
    }

    /**
     * 控制当服务已经停止后，重复调用stop方法，是否抛出服务已经停止异常
     * 默认是true
     *
     * @return true:抛出异常
     */
    protected boolean throwIfStopped() {
        return true;
    }

    /**
     * 服务启动停止，超时时间, 默认是10s
     *
     * @return 超时时间
     */
    protected int timeoutMillis() {
        return 1000 * 10;
    }

    protected interface FunctionEx {
        /**
         * 申请
         *
         * @param l {@link Listener}
         * @throws Throwable 异常信息
         */
        void apply(Listener l) throws Throwable;
    }

    /**
     * Prevent Listener from being executed repeatedly
     *
     * @param listener listener
     * @return FutureListener
     */
    public FutureListener wrap(Listener listener) {
        if (listener == null) {
            return new FutureListener(started);
        }
        if (listener instanceof FutureListener) {
            return (FutureListener) listener;
        }
        return new FutureListener(listener, started);
    }

    public NioEventLoopGroup getWorkGroup() {
        return workGroup;
    }

    public NioEventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public ServerBootstrap getServerBootstrap() {
        return serverBootstrap;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}
