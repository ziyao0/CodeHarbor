package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.crypto.core.CryptoContext;
import com.ziyao.harbor.crypto.core.CryptoContextFactory;
import com.ziyao.harbor.crypto.core.DefaultCryptoContextFactory;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
@Getter
public class EnvironmentDecryptPostProcessor
        extends AbstractEnvironmentDecryptor implements EnvironmentPostProcessor, Ordered {

    private static final int order = Ordered.LOWEST_PRECEDENCE;
    private final CryptoContextFactory contextFactory;

    public EnvironmentDecryptPostProcessor() {
        this.contextFactory = new DefaultCryptoContextFactory();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 创建解密上下文
        CryptoContext context = contextFactory.createContext(environment);

        MutablePropertySources propertySources = environment.getPropertySources();
        environment.getPropertySources().remove(CIPHER_PROPERTY_SOURCE_NAME);
        Map<String, Object> properties = decrypt(context, propertySources);
        if (!Collections.isEmpty(properties)) {
            environment.getPropertySources().addFirst(
                    new SystemEnvironmentPropertySource(
                            CIPHER_PROPERTY_SOURCE_NAME, properties)
            );
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
