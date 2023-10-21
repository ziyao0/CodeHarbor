package com.ziyao.harbor.crypto;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class AbstractAlgorithm implements Algorithm {


    private final String algorithm;

    public AbstractAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }
}
