package com.ziyao.harbor.core.collection;

import com.ziyao.harbor.core.io.IOUtils;
import com.ziyao.harbor.core.utils.Assert;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 将Reader包装为一个按照行读取的Iterator<br>
 * 此对象遍历结束后，应关闭之，推荐使用方式:
 *
 * <pre>
 * LineIterator it = null;
 * try {
 * 	it = new LineIterator(reader);
 * 	while (it.hasNext()) {
 * 		String line = it.nextLine();
 * 		// do something with line
 *    }
 * } finally {
 * 		it.close();
 * }
 * </pre>
 * <p>
 * 此类来自于Apache Commons io
 *
 * @author ziyao zhang
 * @since 2023/10/19
 */
public class LineIter extends ComputeIter<String> implements IterableIter<String>, Closeable, Serializable {

    @Serial
    private static final long serialVersionUID = -2489100770629325670L;

    private final BufferedReader bufferedReader;

    /**
     * 构造
     *
     * @param in      {@link InputStream}
     * @param charset 编码
     * @throws IllegalArgumentException reader为null抛出此异常
     */
    public LineIter(InputStream in, Charset charset) throws IllegalArgumentException {
        this(IOUtils.getReader(in, charset));
    }

    /**
     * 构造
     *
     * @param reader {@link Reader}对象，不能为null
     * @throws IllegalArgumentException reader为null抛出此异常
     */
    public LineIter(Reader reader) throws IllegalArgumentException {
        Assert.notNull(reader, "Reader must not be null");
        this.bufferedReader = IOUtils.getReader(reader);
    }

    // -----------------------------------------------------------------------
    @Override
    protected String computeNext() {
        try {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return null;
                } else if (isValidLine(line)) {
                    return line;
                }
                // 无效行，则跳过进入下一行
            }
        } catch (IOException ioe) {
            close();
            throw new RuntimeException(ioe);
        }
    }

    /**
     * 关闭Reader
     */
    @Override
    public void close() {
        super.finish();
        IOUtils.close(bufferedReader);
    }

    /**
     * 重写此方法来判断是否每一行都被返回，默认全部为true
     *
     * @param line 需要验证的行
     * @return 是否通过验证
     */
    protected boolean isValidLine(String line) {
        return true;
    }
}
