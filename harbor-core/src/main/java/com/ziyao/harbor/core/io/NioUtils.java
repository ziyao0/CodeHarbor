package com.ziyao.harbor.core.io;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Charsets;
import com.ziyao.harbor.core.utils.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class NioUtils {
    /**
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;
    /**
     * 默认中等缓存大小 16384
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 2 << 13;
    /**
     * 默认大缓存大小 32768
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 2 << 14;

    /**
     * 数据流末尾
     */
    public static final int EOF = -1;

    /**
     * 拷贝流 
     * 本方法不会关闭流
     *
     * @param in             输入流
     * @param out            输出流
     * @param bufferSize     缓存大小
     * @param streamProgress 进度条
     * @return 传输的byte数
     */
    public static long copyByNIO(InputStream in, OutputStream out, int bufferSize, StreamProgress streamProgress) {
        return copyByNIO(in, out, bufferSize, -1, streamProgress);
    }

    /**
     * 拷贝流<br>
     * 本方法不会关闭流
     *
     * @param in             输入流
     * @param out            输出流
     * @param bufferSize     缓存大小
     * @param count          最大长度
     * @param streamProgress 进度条
     * @return 传输的byte数
     * @since 5.7.8
     */
    public static long copyByNIO(InputStream in, OutputStream out, int bufferSize, long count, StreamProgress streamProgress)  {
        final long copySize = copy(Channels.newChannel(in), Channels.newChannel(out), bufferSize, count, streamProgress);
        IOUtils.flush(out);
        return copySize;
    }

    /**
     * 拷贝文件Channel，使用NIO，拷贝后不会关闭channel
     *
     * @param inChannel  {@link FileChannel}
     * @param outChannel {@link FileChannel}
     * @return 拷贝的字节数
     * @ IO异常
     * @since 5.5.3
     */
    public static long copy(FileChannel inChannel, FileChannel outChannel)  {
        Assert.notNull(inChannel, "In channel is null!");
        Assert.notNull(outChannel, "Out channel is null!");

        try {
            return copySafely(inChannel, outChannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件拷贝实现
     *
     * <pre>
     * FileChannel#transferTo 或 FileChannel#transferFrom 的实现是平台相关的，需要确保低版本平台的兼容性
     * 例如 android 7以下平台在使用 ZipInputStream 解压文件的过程中，
     * 通过 FileChannel#transferFrom 传输到文件时，其返回值可能小于 totalBytes，不处理将导致文件内容缺失
     *
     * // 错误写法，dstChannel.transferFrom 返回值小于 zipEntry.getSize()，导致解压后文件内容缺失
     * try (InputStream srcStream = zipFile.getInputStream(zipEntry);
     * 		ReadableByteChannel srcChannel = Channels.newChannel(srcStream);
     * 		FileOutputStream fos = new FileOutputStream(saveFile);
     * 		FileChannel dstChannel = fos.getChannel()) {
     * 		dstChannel.transferFrom(srcChannel, 0, zipEntry.getSize());
     *  }
     * </pre>
     *
     * @param inChannel  输入通道
     * @param outChannel 输出通道
     * @return 输入通道的字节数
     * @throws IOException 发生IO错误
     * @author z8g
     * @since 5.7.21
     */
    private static long copySafely(FileChannel inChannel, FileChannel outChannel) throws IOException {
        final long totalBytes = inChannel.size();
        for (long pos = 0, remaining = totalBytes; remaining > 0; ) { // 确保文件内容不会缺失
            final long writeBytes = inChannel.transferTo(pos, remaining, outChannel); // 实际传输的字节数
            pos += writeBytes;
            remaining -= writeBytes;
        }
        return totalBytes;
    }

    /**
     * 拷贝流，使用NIO，不会关闭channel
     *
     * @param in  {@link ReadableByteChannel}
     * @param out {@link WritableByteChannel}
     * @return 拷贝的字节数
     * @ IO异常
     * @since 4.5.0
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out)  {
        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝流，使用NIO，不会关闭channel
     *
     * @param in         {@link ReadableByteChannel}
     * @param out        {@link WritableByteChannel}
     * @param bufferSize 缓冲大小，如果小于等于0，使用默认
     * @return 拷贝的字节数
     * @ IO异常
     * @since 4.5.0
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out, int bufferSize)  {
        return copy(in, out, bufferSize, null);
    }

    /**
     * 拷贝流，使用NIO，不会关闭channel
     *
     * @param in             {@link ReadableByteChannel}
     * @param out            {@link WritableByteChannel}
     * @param bufferSize     缓冲大小，如果小于等于0，使用默认
     * @param streamProgress {@link StreamProgress}进度处理器
     * @return 拷贝的字节数
     * @ IO异常
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out, int bufferSize, StreamProgress streamProgress)  {
        return copy(in, out, bufferSize, -1, streamProgress);
    }

    /**
     * 拷贝流，使用NIO，不会关闭channel
     *
     * @param in             {@link ReadableByteChannel}
     * @param out            {@link WritableByteChannel}
     * @param bufferSize     缓冲大小，如果小于等于0，使用默认
     * @param count          读取总长度
     * @param streamProgress {@link StreamProgress}进度处理器
     * @return 拷贝的字节数
     * @ IO异常
     * @since 5.7.8
     */
    public static long copy(ReadableByteChannel in, WritableByteChannel out, int bufferSize, long count, StreamProgress streamProgress)  {
        return new ChannelCopier(bufferSize, count, streamProgress).copy(in, out);
    }

    /**
     * 从流中读取内容，读取完毕后并不关闭流
     *
     * @param channel 可读通道，读取完毕后并不关闭通道
     * @param charset 字符集
     * @return 内容
     * @ IO异常
     * @since 4.5.0
     */
    public static String read(ReadableByteChannel channel, Charset charset)  {
        FastByteArrayOutputStream out = read(channel);
        return null == charset ? out.toString() : out.toString(charset);
    }

    /**
     * 从流中读取内容，读到输出流中
     *
     * @param channel 可读通道，读取完毕后并不关闭通道
     * @return 输出流
     * @ IO异常
     */
    public static FastByteArrayOutputStream read(ReadableByteChannel channel)  {
        final FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        copy(channel, Channels.newChannel(out));
        return out;
    }

    /**
     * 从FileChannel中读取UTF-8编码内容
     *
     * @param fileChannel 文件管道
     * @return 内容
     * @ IO异常
     */
    public static String readUtf8(FileChannel fileChannel)  {
        return read(fileChannel, Charsets.CHARSET_UTF_8);
    }

    /**
     * 从FileChannel中读取内容，读取完毕后并不关闭Channel
     *
     * @param fileChannel 文件管道
     * @param charsetName 字符集
     * @return 内容
     * @ IO异常
     */
    public static String read(FileChannel fileChannel, String charsetName)  {
        return read(fileChannel, Charsets.forName(charsetName));
    }

    /**
     * 从FileChannel中读取内容
     *
     * @param fileChannel 文件管道
     * @param charset     字符集
     * @return 内容
     * @ IO异常
     */
    public static String read(FileChannel fileChannel, Charset charset)  {
        MappedByteBuffer buffer;
        try {
            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Strings.toString(buffer, charset);
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }
}
