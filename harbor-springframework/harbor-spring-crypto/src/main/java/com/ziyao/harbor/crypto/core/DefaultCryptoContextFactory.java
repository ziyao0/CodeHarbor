package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.crypto.EnvironmentExtractor;
import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.utils.TextCipherUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DefaultCryptoContextFactory implements CryptoContextFactory {


    @Override
    public CryptoContext createContext(ConfigurableApplicationContext applicationContext) {

        CodebookProperties properties = loadEncryptorPropertiesFromEnvironment(applicationContext);

        TextCipherProvider textCipherProvider = createTextCipherProvider(properties);

        ConfigurableCryptoContext context = new ConfigurableCryptoContext();
        context.setProperties(properties);
        context.setTextCipherProvider(textCipherProvider);
        context.setPropertyResolver(new PropertyResolver(textCipherProvider));
        return context;
    }

    private TextCipherProvider createTextCipherProvider(CodebookProperties properties) {
        List<TextCipher> textCiphers = TextCipherUtils.loadCipher(properties);
        return new TextCipherProvider(textCiphers);
    }

    private CodebookProperties loadEncryptorPropertiesFromEnvironment(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        CodebookProperties main = EnvironmentExtractor.extractProperties(environment, CodebookProperties.class);
        CodebookProperties external = null;
        if (null != main) {
            external = EnvironmentExtractor.extractProperties(main.getLocation(), CodebookProperties.class);
        }
        // 合并配置
        return CodebookProperties.merge(main, external);
    }
}
