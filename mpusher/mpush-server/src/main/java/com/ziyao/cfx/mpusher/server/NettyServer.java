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
                //Initialize server connection queue size
                .option(ChannelOption.SO_BACKLOG, 1024)
                /*
                 * Used to manipulate the size of the receiving buffer and sending buffer,
                 * the receiving buffer is used to store the data received in the network protocol station,
                 * Until the application program reads successfully,
                 * the sending buffer is used to save the sending data until the sending is successful.
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
                 */
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(32 * 1024, 64 * 1024))
                // Use application layer heartbeat
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(
                        new ServerChannelInitializer()
                );
    }

    public static void main(String[] args) {
        new NettyServer().start(8888);
    }
}
