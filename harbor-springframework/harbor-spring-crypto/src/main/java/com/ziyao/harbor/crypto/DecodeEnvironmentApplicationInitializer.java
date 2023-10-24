package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.lang.NonNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * @author ziyao zhang
 * @since 2023/10/24
 */
public class DecodeEnvironmentApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
//        CipherProperties properties =    CipherFactory.loadEncryptorPropertiesFromEnvironment(applicationContext);

        applicationContext.getBeanFactory()
                .registerSingleton(EncryptorContext.class.getName(), context);
        decryptProperties(context, applicationContext);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 14;
    }
}
