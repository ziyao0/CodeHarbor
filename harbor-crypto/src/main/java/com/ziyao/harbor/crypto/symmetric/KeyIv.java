package com.ziyao.harbor.crypto.symmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
import com.ziyao.harbor.crypto.Algorithm;
import lombok.Getter;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class KeyIv extends AbstractAlgorithm {

    private String key;

    private String iv;

    private Mode mode;

    private byte[] keyBytes;
    private byte[] ivBytes;

    public KeyIv() {
        super(Algorithm.SM4);
    }

    public KeyIv(String algorithm, byte[] keyBytes, byte[] ivBytes) {
        super(algorithm);
        this.keyBytes = keyBytes;
        this.ivBytes = ivBytes;
        this.key = HexUtils.encodeHexStr(keyBytes);
        this.iv = HexUtils.encodeHexStr(ivBytes);
        this.mode = Mode.CBC;
    }

    public static KeyIv merge(KeyIv kv1, KeyIv kv2) {
        KeyIv keyIv = new KeyIv();
        keyIv.merge(kv1);
        keyIv.merge(kv2);
        return keyIv;
    }

    public void merge(KeyIv keyIv) {
        if (null == keyIv) {
            return;
        }
        String key = keyIv.getKey();
        String iv = keyIv.getIv();
        Mode mode = keyIv.getMode();
        if (Strings.hasText(key)) {
            this.key = key;
        }
        if (Strings.hasText(iv)) {
            this.iv = iv;
        }
        if (null != mode) {
            this.mode = mode;
        }
    }
}
