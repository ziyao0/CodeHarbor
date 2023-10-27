package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.Properties;
import com.ziyao.harbor.core.lang.NonNull;
import org.springframework.core.env.PropertySource;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public class CryptoPropertySource extends PropertySource<Properties<?>> {


    public CryptoPropertySource(String name, Properties<?> source) {
        super(name, source);
    }

    @NonNull
    @Override
    public Properties<?> getProperty(@NonNull String name) {
        return this.source;
    }
}
