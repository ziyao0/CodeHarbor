package com.harbor.boot.autoconfigure.crypto;

import com.ziyao.harbor.crypto.EnvironmentDecryptApplicationInitializer;
import com.ziyao.harbor.crypto.core.CipherContextFactory;
import com.ziyao.harbor.crypto.core.CodebookProperties;
import com.ziyao.harbor.crypto.core.DefaultCipherContextFactory;
import com.ziyao.harbor.crypto.druid.DataSourceBeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ziyao zhang
 * @since 2023/10/26
 */
@Configuration
@AutoConfigureBefore(EncryptionBootstrapConfiguration.class)
@EnableConfigurationProperties(CodebookProperties.class)
public class CryptoBootstrapConfiguration {


    @Bean
    @ConditionalOnClass(name = {
            "com.ziyao.harbor.crypto.TextCipher",
            "com.ziyao.harbor.core.codec.StringCodec"
    })
    public CipherContextFactory cipherContextFactory() {
        return new DefaultCipherContextFactory();
    }

    @Bean
    public EnvironmentDecryptApplicationInitializer codecEnvironmentApplicationInitializer() {
        return new EnvironmentDecryptApplicationInitializer(cipherContextFactory());
    }

    @Configuration
    @ConditionalOnClass(name = {
            "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
            "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure"
    })
    @ConditionalOnBean(CipherContextFactory.class)
    @Import(DataSourceBeanPostProcessor.Registrar.class)
    public static class DataSourceAutoConfiguration {

    }
}
