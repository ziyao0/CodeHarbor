package com.ziyao.harbor.core.codec;

import com.ziyao.harbor.core.utils.HexUtils;

import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class HexCodec implements StringCodec {


    public static final HexCodec CODEC = new HexCodec();


    @Override
    public String encode(byte[] data) {
        return HexUtils.encodeHexStr(data);
    }

    @Override
    public String encode(byte[] data, Charset charset) {
        return HexUtils.encodeHexStr(data);
    }

    @Override
    public byte[] decode(CharSequence encoded) {
        return HexUtils.decodeHex(encoded);
    }

    @Override
    public byte[] decode(CharSequence encoded, Charset charset) {
        return HexUtils.decodeHex(encoded);
    }
}
