package com.ziyao.cfx.mpusher.server;

import com.ziyao.cfx.mpusher.core.AbstractStarter;
import com.ziyao.cfx.mpusher.server.init.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class NettyServer extends AbstractStarter {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(NettyServer.class);


    public NettyServer() {
        super(new ServerBootstrap(), new NioEventLoopGroup(1));
    }

    @Override
    public void init() {
        LOGGER.info("Initialize Bootstrap parameters...");
        super.getServerBootstrap().group(super.getBossGroup(), super.getWorkGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //Initialize server connection queue size 初始化服务器连队大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                /*
                 * Used to manipulate the size of the receiving buffer and sending buffer,
                 * the receiving buffer is used to store the data received in the network protocol station,
                 * Until the application program reads successfully,
                 * the sending buffer is used to save the sending data until the sending is successful.
                 * <p>
                 * TCP层面的接收和发送缓冲区大小设置，
                 * 在Netty中分别对应ChannelOption的SO_SNDBUF和SO_RCVBUF，
                 * 需要根据推送消息的大小，合理设置，对于海量长连接，通常32K是个不错的选择。
                 */
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                /*
                 *  <p>
                 *  A new ByteBuf memory pool is implemented in Netty 4,
                 *  which is a pure Java version of jemalloc (also used by Facebook). Now,
                 *  Netty will no longer waste memory bandwidth by filling the buffer with zeros.
                 *  However, since it does not rely on GC, developers need to be careful about memory leaks.
                 *  If you forget to release the buffer in the handler, the memory usage will increase indefinitely.
                 *  Netty does not use the memory pool by default, it needs to be specified when creating the client or server.
                 * </p>
                 * 在Netty 4中实现了一个新的ByteBuf内存池，它是一个纯Java版本的 jemalloc （Facebook也在用）。
                 * 现在，Netty不会再因为用零填充缓冲区而浪费内存带宽了。不过，由于它不依赖于GC，开发人员需要小心内存泄漏。
                 * 如果忘记在处理程序中释放缓冲区，那么内存使用率会无限地增长。
                 * Netty默认不使用内存池，需要在创建客户端或者服务端的时候进行指定
                 */
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                /*
                 * <p>
                 *  Generally speaking, if our business is relatively small, we use synchronous processing.
                 *  When the business reaches a certain scale, an optimization method is asynchronous.
                 *  Async is a good way to improve throughput. However, compared with asynchronous,
                 *  synchronization has a natural negative feedback mechanism, that is, if the back end is slow,
                 *  the front will also slow down and can be adjusted automatically. But async is different.
                 *  Async is like a dam bursting a bank. The flood is unimpeded.
                 *  If effective current limiting measures are not taken at this time, it is easy to overwhelm the back end.
                 *  It is not the worst situation if the back end is rushed down all of a sudden,
                 *  for fear of rushing the back end to death. At this time, the back end will become particularly slow.
                 *  If the previous application uses some unbounded resources at this time, it may kill itself.
                 *  So the pit to be introduced now is about the ChannelOutboundBuffer in Netty.
                 *  This buffer is used when netty writes data to the channel.
                 *  There is a buffer buffer, which can improve the throughput of the network (each channel has one such buffer).
                 *  The initial size is 32 (32 elements, not bytes), but if it exceeds 32, it will double and keep growing.
                 *  Most of the time there is no problem, but it is very slow when it comes to the opposite
                 *  end (the slowness of the opposite end refers to the slowness of the opposite end to process TCP packets,
                 *  for example, this may be the case when the opposite end has a particularly high load) There will be a problem at that time.
                 *  If you continue to write data at this time, the buffer will continue to grow,
                 *  and finally there may be a problem (in our case, we started to eat swap, and finally the process was killed by the linux killer).
                 *  Why is this place a pit? Because most of the time we write data to a channel to determine whether the channel is active,
                 *  but we often ignore this slow situation. How to solve this problem? Although ChannelOutboundBuffer is unbounded,
                 *  it can be configured with a high-water mark and low-water mark. When the size of the buffer exceeds the high-water mark,
                 *  the isWritable of the corresponding channel will become false.
                 *  When the size of the buffer is lower than the low-water mark ,
                 *  IsWritable will become true. So the application should judge isWritable, if it is false, don't write data anymore.
                 *  The high-water mark and low-water mark are the number of bytes. The default high-water mark is 64K and the low-water mark is 32K.
                 *  We can make a reasonable plan based on how many connections and system resources our application needs to support.
                 * </p>
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
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(32 * 1024, 64 * 1024))
                // Use application layer heartbeat 使用应用层心跳
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(
                        new ServerChannelInitializer()
                );
    }

    public static void main(String[] args) {
        new NettyServer().start(8888);
    }
}
