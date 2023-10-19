package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.codec.Decoder;
import com.ziyao.harbor.core.codec.Encoder;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class AbstractAlgorithm implements Algorithm {


    private final String algorithm;

    public Decoder<CharSequence, byte[]> decoder;

    public AbstractAlgorithm(String algorithm, Decoder<CharSequence, byte[]> decoder) {
        this.algorithm = algorithm;
        this.decoder = decoder;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }
}
