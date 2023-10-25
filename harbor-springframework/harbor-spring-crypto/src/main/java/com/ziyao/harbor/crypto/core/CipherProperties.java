package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.crypto.asymmetric.KeyPair;
import com.ziyao.harbor.crypto.symmetric.KeyIv;
import com.ziyao.harbor.crypto.utils.ConstantPool;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(ConstantPool.cipher_prefix)
public class CipherProperties extends Properties<CipherProperties> {

    private Set<Type> types;

    private KeyIv sm4;

    private KeyPair sm2;

    private String configPath;

    public static CipherProperties merge(CipherProperties main, CipherProperties external) {

        if (null == main) {
            main = new CipherProperties();
        }
        if (null == external) {
            external = new CipherProperties();
        }

        CipherProperties properties = new CipherProperties();

        properties.setSm4(KeyIv.merge(main.getSm4(), external.getSm4()));
        properties.setSm2(KeyPair.merge(main.getSm2(), external.getSm2()));
        properties.setTypes(main.getTypes());
        properties.setConfigPath(main.getConfigPath());
        return properties;
    }

    @Override
    public String getPrefix() {
        return ConstantPool.cipher_prefix;
    }

    public enum Type {
        sm2, sm4;
    }
}
