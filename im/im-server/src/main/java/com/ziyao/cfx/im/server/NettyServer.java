package com.ziyao.cfx.im.server;

import com.ziyao.cfx.im.core.AbstractStarter;
import com.ziyao.cfx.im.core.NettyConfigurationCenter;
import com.ziyao.cfx.im.server.init.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class NettyServer extends AbstractStarter {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(NettyServer.class);

    private final int port;


    public NettyServer(int port) {
        super(new ServerBootstrap(), new NioEventLoopGroup(NettyConfigurationCenter.Core.boss_group_threads));
        init();
        this.port = port;
        LOGGER.info("Netty核心配置初始化完成!");
    }

    @Override
    public void init() {
        super.getServerBootstrap().group(super.getBossGroup(), super.getWorkGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //Initialize server connection queue size 初始化服务器连队大小
                .option(ChannelOption.SO_BACKLOG, NettyConfigurationCenter.Server.so_backlog)
                /*
                 * TCP层面的接收和发送缓冲区大小设置，
                 * 在Netty中分别对应ChannelOption的SO_SNDBUF和SO_RCVBUF，
                 * 需要根据推送消息的大小，合理设置，对于海量长连接，通常32K是个不错的选择。
                 */
                .childOption(ChannelOption.SO_SNDBUF, NettyConfigurationCenter.Server.so_snd_buf)
                .childOption(ChannelOption.SO_RCVBUF, NettyConfigurationCenter.Server.so_rcv_buf)
                /*
                 * 在Netty 4中实现了一个新的ByteBuf内存池，它是一个纯Java版本的 jemalloc （Facebook也在用）。
                 * 现在，Netty不会再因为用零填充缓冲区而浪费内存带宽了。不过，由于它不依赖于GC，开发人员需要小心内存泄漏。
                 * 如果忘记在处理程序中释放缓冲区，那么内存使用率会无限地增长。
                 * Netty默认不使用内存池，需要在创建客户端或者服务端的时候进行指定
                 */
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                /*
                 * <p>
                 * 这个坑其实也不算坑，只是因为懒，该做的事情没做。一般来讲我们的业务如果比较小的时候我们用同步处理，等业务到一定规模的时候，一个优化手段就是异步化。
                 * 异步化是提高吞吐量的一个很好的手段。但是，与异步相比，同步有天然的负反馈机制，也就是如果后端慢了，前面也会跟着慢起来，可以自动的调节。
                 * 但是异步就不同了，异步就像决堤的大坝一样，洪水是畅通无阻。如果这个时候没有进行有效的限流措施就很容易把后端冲垮。
                 * 如果一下子把后端冲垮倒也不是最坏的情况，就怕把后端冲的要死不活。
                 * 这个时候，后端就会变得特别缓慢，如果这个时候前面的应用使用了一些无界的资源等，就有可能把自己弄死。
                 * 那么现在要介绍的这个坑就是关于Netty里的ChannelOutboundBuffer这个东西的。
                 * 这个buffer是用在netty向channel write数据的时候，有个buffer缓冲，这样可以提高网络的吞吐量(每个channel有一个这样的buffer)。
                 * 初始大小是32(32个元素，不是指字节)，但是如果超过32就会翻倍，一直增长。
                 * 大部分时候是没有什么问题的，但是在碰到对端非常慢(对端慢指的是对端处理TCP包的速度变慢，比如对端负载特别高的时候就有可能是这个情况)的时候就有问题了，
                 * 这个时候如果还是不断地写数据，这个buffer就会不断地增长，最后就有可能出问题了(我们的情况是开始吃swap，最后进程被linux killer干掉了)。
                 * 为什么说这个地方是坑呢，因为大部分时候我们往一个channel写数据会判断channel是否active，但是往往忽略了这种慢的情况。
                 *
                 * 那这个问题怎么解决呢？其实ChannelOutboundBuffer虽然无界，但是可以给它配置一个高水位线和低水位线，
                 * 当buffer的大小超过高水位线的时候对应channel的isWritable就会变成false，
                 * 当buffer的大小低于低水位线的时候，isWritable就会变成true。所以应用应该判断isWritable，如果是false就不要再写数据了。
                 * 高水位线和低水位线是字节数，默认高水位是64K，低水位是32K，我们可以根据我们的应用需要支持多少连接数和系统资源进行合理规划。
                 * </p>
                 */
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(
                        NettyConfigurationCenter.Server.write_buffer_water_mark_low,
                        NettyConfigurationCenter.Server.write_buffer_water_mark_high))
                // Use application layer heartbeat 使用应用层心跳
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(
                        new ServerChannelInitializer()
                );
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
     */
    public void start() {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        LOGGER.info("netty server is starting ...");
        final ChannelFuture future = getServerBootstrap().bind(this.port);
        future.addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                started.set(true);
                LOGGER.info("Netty Server is started success!");
                LOGGER.info("Netty Server port:{}", this.port);
            } else {
                LOGGER.error("Netty Server is started fail!");
                started.set(false);
            }
        });
    }

    public static void main(String[] args) {
        new NettyServer(8888).start();
    }
}
