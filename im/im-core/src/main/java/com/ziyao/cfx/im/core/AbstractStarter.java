package com.ziyao.cfx.im.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public abstract class AbstractStarter implements Starter {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(AbstractStarter.class);

    private static final Pattern PORT_PATTERN = Pattern.compile(
            "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])");
    private static final Pattern IP_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
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
    public void start(SocketAddress socketAddress) {
        ChannelFuture channelFuture = bootstrap.connect(socketAddress);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                LOGGER.info("Netty Server Address：{}!", socketAddress);
                MISSIONS_RETRIED.set(0);
                LOGGER.info("Netty Client Shake Hands SUCCESS! Reset retries!");
            } else {
                future.channel().eventLoop().schedule(() -> {
                    try {
                        int retryCount = MISSIONS_RETRIED.incrementAndGet();
                        LOGGER.error("The client reconnect! The number of current reconnections: {}", retryCount);
                        start(socketAddress);
                    } catch (Exception e) {
                        LOGGER.error("connect exception!", e);
                    }
                }, getMissionsRetried(), TimeUnit.MILLISECONDS);
                LOGGER.error("reconnect!");
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
        if (MISSIONS_RETRIED.get() <= 10) {
            return 5000;
        } else if (MISSIONS_RETRIED.get() <= 30) {
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
     * Fetch Port by default
     *
     * @return port
     */
    protected int fetchPort(String address) throws UnknownHostException {
        String val = address.split(":")[1];
        int port = Integer.parseInt(val);
        if (checkPort(port)) {
            return port;
        }
        throw new UnknownHostException(address);
    }

    /**
     * Fetch Port by default
     *
     * @return port
     */
    protected String fetchIP(String address) throws UnknownHostException {
        String ip = address.split(":")[0];
        if (checkIP(ip)) {
            return ip;
        }
        throw new UnknownHostException(address);
    }

    /**
     * Verify port legitimacy
     *
     * @param port Resource port
     * @return Return the verification result , {@link Boolean#TRUE} is legitimate
     */
    private boolean checkPort(Integer port) {
        if (port != null && port > 0) {
            return PORT_PATTERN.matcher(String.valueOf(port)).matches();
        }
        return false;
    }

    /**
     * Verify IP legitimacy
     *
     * @param ip ip
     * @return Return the verification result , {@link Boolean#TRUE} is legitimate
     */
    private boolean checkIP(String ip) {
        if (ip != null && ip.length() > 0) {
            return IP_PATTERN.matcher(ip).matches();
        }
        return false;
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
