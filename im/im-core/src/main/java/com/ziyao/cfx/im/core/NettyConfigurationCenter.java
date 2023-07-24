package com.ziyao.cfx.im.core;

/**
 * netty 配置中心
 *
 * @author zhangziyao
 * @date 2023/4/23
 */
public interface NettyConfigurationCenter {

    interface Core {
        //nio event loop group threads
        int boss_group_threads = 1;
    }

    interface Server {
        //初始化服务器连接队列大小
        int so_backlog = 1024;
        // tcp层面发送缓存区大小
        int so_snd_buf = 32 * 1024;
        // tcp层面接收缓存区大小
        int so_rcv_buf = 32 * 1024;
        /**
         * buffer是netty向channel写书籍的时候，又一个buffer缓存，是为了提高网络的吞吐量
         * 初始大小是32个元素，超过32就会翻倍，一直增长，大部分时候是没有什么问题的，但是碰到
         * 对端(对端慢指的是对端处理TCP包的速度变慢，比如对端负载特别高的时候就有可能是这个情况)
         * 非常慢的时候就有问题了，这个时候如果还是不断的写数据，这个buffer就会不断的增长，
         * 最后就有可能出问题了(我们的情况是开始吃swap，最后进程被linux killer干掉了)。
         * 为什么说这个地方是坑呢，因为大部分时候我们往一个channel写数据会判断channel是否active，
         * 但是往往忽略了这种慢的情况。
         * 其实channelOutBoundBuffer虽然无界，但是可以配置一个高水位线和低水位线，当buffer的
         * 大小超过高水位线的时候对应channel的isWritable就会变成false，当buffer的大小低于低
         * 水位线的时候，isWritable就会变成true。
         * <p>
         * 低水位线
         */
        int write_buffer_water_mark_low = 32 * 1024;
        //高水位线
        int write_buffer_water_mark_high = 64 * 1024;
    }

    interface Client {

    }
}
