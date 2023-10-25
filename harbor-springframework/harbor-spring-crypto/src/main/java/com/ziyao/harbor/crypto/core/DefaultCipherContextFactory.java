package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.utils.Assert;
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
public class DefaultCipherContextFactory implements CipherContextFactory {


    @Override
    public CipherContext createContext(ConfigurableApplicationContext applicationContext) {

        CipherProperties properties = loadEncryptorPropertiesFromEnvironment(applicationContext);

        TextCipherProvider textCipherProvider = createTextCipherProvider(properties);

        ConfigurableCipherContext context = new ConfigurableCipherContext();
        context.setProperties(properties);
        context.setTextCipherProvider(textCipherProvider);
        context.setPropertyResolver(new PropertyResolver(textCipherProvider));
        return context;
    }

    private TextCipherProvider createTextCipherProvider(CipherProperties properties) {
        List<TextCipher> textCiphers = TextCipherUtils.loadCipher(properties);
        return new TextCipherProvider(textCiphers);
    }

    private CipherProperties loadEncryptorPropertiesFromEnvironment(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        CipherProperties main = EnvironmentExtractor.extractProperties(environment, CipherProperties.class);
        Assert.notNull(main, "未获取到本地配置的秘钥信息！");
        CipherProperties external = EnvironmentExtractor.extractProperties(main.getConfigPath(), CipherProperties.class);
        Assert.notNull(external, "未获取到外部配置的秘钥信息！");

        // 合并配置
        return CipherProperties.merge(main, external);
    }
}
