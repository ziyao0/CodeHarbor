package com.ziyao.harbor.core.codec;

import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface StringCodec extends
        Encoder<byte[], String>, Decoder<CharSequence, byte[]> {

    /**
     * 编码
     *
     * @param data    待编码的数据
     * @param charset 字符编码
     * @return 返回编码后的数据
     */
    String encode(byte[] data, Charset charset);

    /**
     * 解码
     *
     * @param encoded 代解码的数据
     * @param charset 字符编码
     * @return 解码后的字符串
     */
    byte[] decode(CharSequence encoded, Charset charset);
}
