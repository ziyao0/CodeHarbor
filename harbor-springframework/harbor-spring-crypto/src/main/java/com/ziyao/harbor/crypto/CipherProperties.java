package com.ziyao.harbor.crypto;

import com.ziyao.harbor.crypto.symmetric.Mode;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
@Getter
@Configuration
@ConfigurationProperties("com.ziyao.crypto.cipher")
public class CipherProperties {

    private Set<Type> types;

    private Sm4Properties sm4;

    private Sm2Properties sm2;

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public void setSm4(Sm4Properties sm4) {
        this.sm4 = sm4;
    }

    public void setSm2(Sm2Properties sm2) {
        this.sm2 = sm2;
    }

    public enum Type {
        sm2, sm4;
    }

    @Getter
    public static class Sm4Properties {

        private Mode mode = Mode.CBC;
        private String key;
        private String iv;

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }
    }


    @Getter
    public static class Sm2Properties {
        private String pubKey;
        private String priKey;

        public void setPubKey(String pubKey) {
            this.pubKey = pubKey;
        }

        public void setPriKey(String priKey) {
            this.priKey = priKey;
        }
    }
}
