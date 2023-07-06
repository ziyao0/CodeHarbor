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
     * <p>
     * 设置Netty的ResourceLeakDetector级别为PARANOID（极度谨慎）级别。ResourceLeakDetector是Netty框架中用于检测资源泄漏的工具。
     * 资源泄漏是指在使用完资源后没有及时释放或关闭资源，导致资源无法回收和释放的情况。在网络编程中，资源泄漏可能会导致内存泄漏和性能问题。
     * ResourceLeakDetector的级别用于控制资源泄漏检测的严格程度，有以下几个级别可供选择：
     * <p>
     * DISABLED：禁用资源泄漏检测。
     * SIMPLE：简单模式，只检测少数关键资源。
     * ADVANCED：进一步增加了检测的资源类型。
     * PARANOID：极度谨慎模式，对所有可能的资源进行检测。
     * <p>
     * 需要注意的是，设置ResourceLeakDetector的级别是一个全局设置，将影响整个Netty应用程序的资源泄漏检测。在开发和调试阶段，
     * 将级别设置为PARANOID可以帮助发现潜在的资源泄漏问题，但在生产环境中可能会带来性能开销，因此应根据实际情况进行选择。
     * <p>
     * 在生产环境中，为了平衡性能和资源泄漏检测的需要，通常建议将Netty的ResourceLeakDetector级别设置为SIMPLE或ADVANCED。
     * 这样可以在一定程度上检测资源泄漏问题，同时减少对性能的影响。
     * <p>
     * 以下是一些建议的配置：
     * <p>
     * {@code  ResourceLeakDetector#setLevel(ResourceLeakDetector.Level.SIMPLE)}
     * 这将设置ResourceLeakDetector的级别为SIMPLE。它会检测一些关键资源的泄漏，对于大多数生产环境而言是一个合理的折中选择。
     * <p>
     * {@code  ResourceLeakDetector#setLevel(ResourceLeakDetector.Level.ADVANCED)}
     * 这将设置ResourceLeakDetector的级别为ADVANCED。它会进一步增加对资源泄漏的检测范围，但相应地可能会对性能产生一些额外的开销。在需要更严格的资源泄漏检测的生产环境中，可以选择这个级别。
     * <p>
     * 同时，需要注意以下几点：
     * <p>
     * ResourceLeakDetector的级别设置是全局的，对整个Netty应用程序生效。
     * 在生产环境中，通常建议将日志级别设置为适当的水平（例如INFO或WARN），以避免日志过于冗长，同时仍然能够在需要时记录重要的错误和警告信息。
     * 此外，还应该确保代码中没有明显的资源泄漏问题，并进行合理的资源管理和释放，以最大程度地减少潜在的泄漏风险。
     *
     * @param port The port number
     */
    @Override
    public void start(final int port) {
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
    public void start(SocketAddress socketAddress) {
        ChannelFuture channelFuture = bootstrap.connect(socketAddress);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                LOGGER.info("Netty服务端地址：{}!", socketAddress);
                MISSIONS_RETRIED.set(0);
                LOGGER.info("Netty客户端启动成功！");
            } else {
                future.channel().eventLoop().schedule(() -> {
                    try {
                        int retryCount = MISSIONS_RETRIED.incrementAndGet();
                        LOGGER.error("客户端重新连接！当前重新连接次数: {}", retryCount);
                        start(socketAddress);
                    } catch (Exception e) {
                        LOGGER.error("连接异常!", e);
                    }
                }, getMissionsRetried(), TimeUnit.MILLISECONDS);
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
