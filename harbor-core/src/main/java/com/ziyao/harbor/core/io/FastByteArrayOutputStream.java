package com.ziyao.harbor.core.io;

import com.ziyao.harbor.core.utils.Charsets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public class FastByteArrayOutputStream extends OutputStream {

    private final FastByteBuffer buffer;

    /**
     * 构造
     */
    public FastByteArrayOutputStream() {
        this(1024);
    }

    /**
     * 构造
     *
     * @param size 预估大小
     */
    public FastByteArrayOutputStream(int size) {
        buffer = new FastByteBuffer(size);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        buffer.append(b, off, len);
    }

    @Override
    public void write(int b) {
        buffer.append((byte) b);
    }

    public int size() {
        return buffer.size();
    }

    /**
     * 此方法无任何效果，当流被关闭后不会抛出IOException
     */
    @Override
    public void close() {
        // nop
    }

    public void reset() {
        buffer.reset();
    }

    /**
     * 写出
     *
     * @param out 输出流
     * @throws RuntimeException IO异常
     */
    public void writeTo(OutputStream out) throws RuntimeException {
        final int index = buffer.index();
        if (index < 0) {
            // 无数据写出
            return;
        }
        byte[] buf;
        try {
            for (int i = 0; i < index; i++) {
                buf = buffer.array(i);
                out.write(buf);
            }
            out.write(buffer.array(index), 0, buffer.offset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 转为Byte数组
     *
     * @return Byte数组
     */
    public byte[] toByteArray() {
        return buffer.toArray();
    }

    @Override
    public String toString() {
        return toString(Charsets.defaultCharset());
    }

    /**
     * 转为字符串
     *
     * @param charsetName 编码
     * @return 字符串
     */
    public String toString(String charsetName) {
        return toString(Charsets.forName(charsetName));
    }

    /**
     * 转为字符串
     *
     * @param charset 编码,null表示默认编码
     * @return 字符串
     */
    public String toString(Charset charset) {
        return new String(toByteArray(), charset);
    }

}
