package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.Properties;
import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.symmetric.Mode;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/10/26
 */
@Getter
public abstract class Codebook<T extends Codebook<T>> extends Properties<T> {

    private Set<Type> types = Collections.emptySet();

    private KeyIv sm4 = new KeyIv();

    private KeyPair sm2 = new KeyPair();

    public enum Type {
        sm2, sm4;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public void setSm4(KeyIv sm4) {
        this.sm4 = sm4;
    }

    public void setSm2(KeyPair sm2) {
        this.sm2 = sm2;
    }

    @Getter
    public static class KeyIv extends AbstractAlgorithm {

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

        public void setKey(String key) {
            this.key = key;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public void setKeyBytes(byte[] keyBytes) {
            this.keyBytes = keyBytes;
        }

        public void setIvBytes(byte[] ivBytes) {
            this.ivBytes = ivBytes;
        }

        @Override
        public String toString() {
            return "KeyIv{" +
                    "key='" + key + '\'' +
                    ", iv='" + iv + '\'' +
                    ", mode=" + mode +
                    '}';
        }
    }

    @Getter
    public static class KeyPair extends AbstractAlgorithm {

        /**
         * 公钥.
         */
        private String publicKey;

        /**
         * 私钥.
         */
        private String privateKey;
        /**
         * 公钥.
         */
        private byte[] pubKey;

        /**
         * 私钥.
         */
        private byte[] priKey;


        public KeyPair(String algorithm, byte[] pubKey, byte[] priKey) {
            super(algorithm);
            this.priKey = priKey;
            this.pubKey = pubKey;
            this.publicKey = HexUtils.encodeHexStr(pubKey);
            this.privateKey = HexUtils.encodeHexStr(priKey);
        }

        public KeyPair() {
            super(Algorithm.SM2);
        }

        public static KeyPair merge(KeyPair k1, KeyPair k2) {
            KeyPair keyPair = new KeyPair();
            keyPair.merge(k1);
            keyPair.merge(k2);
            return keyPair;
        }

        public void merge(KeyPair keyPair) {
            if (null == keyPair) {
                return;
            }
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();
            if (Strings.hasText(publicKey)) {
                this.publicKey = publicKey;
            }
            if (Strings.hasText(privateKey)) {
                this.privateKey = privateKey;
            }
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public void setPubKey(byte[] pubKey) {
            this.pubKey = pubKey;
        }

        public void setPriKey(byte[] priKey) {
            this.priKey = priKey;
        }

        @Override
        public String toString() {
            return "KeyPair{" +
                    "publicKey='" + publicKey + '\'' +
                    ", privateKey='" + privateKey + '\'' +
                    '}';
        }
    }

}
