package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.core.CipherProperties;
import com.ziyao.harbor.crypto.core.ObjectPropertySource;
import com.ziyao.harbor.crypto.core.Properties;
import com.ziyao.harbor.crypto.utils.ConstantPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public abstract class EnvironmentExtractor implements Extractor<ConfigurableEnvironment, Properties<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentExtractor.class);


    private static final EnvironmentExtractor ENVIRONMENT_EXTRACTOR;

    static {
        ENVIRONMENT_EXTRACTOR = new EnvironmentExtractor() {
            @Override
            public Properties<?> extract(ConfigurableEnvironment environment) {
                return super.extract(environment);
            }
        };
    }


    @Override
    public Properties<?> extract(ConfigurableEnvironment environment) {
        try {
            if (null == environment) {
                return null;
            }
            return doExtract(environment);
        } catch (Exception e) {
            LOGGER.error("提取配置异常！", e);
        }
        return null;
    }

    private Properties<?> doExtract(ConfigurableEnvironment environment) {

        PropertySource<?> propertySource = environment.getPropertySources().get(ConstantPool.properties_prefix);
        environment.getPropertySources().remove(ConstantPool.properties_prefix);
        if (propertySource != null) {
            Properties<?> properties = (Properties<?>) propertySource.getProperty(ConstantPool.properties_prefix);
            if (properties != null) {
                return Binder.get(environment)
                        .bind(properties.getPrefix(), Bindable.of(properties.getClass()))
                        .orElseGet(() -> null);
            }
        }
        return null;
    }

    /**
     * 从环境变量中提取密码相关配置
     *
     * @param environment 环境变量
     * @return {@link Properties}
     */
    @SuppressWarnings("unchecked")
    public static <T> T extractProperties(ConfigurableEnvironment environment, Class<? extends Properties<T>> clazz) {
        try {
            Properties<T> properties = clazz.getDeclaredConstructor().newInstance();
            environment.getPropertySources().addFirst(new ObjectPropertySource(ConstantPool.properties_prefix, properties));
            return (T) ENVIRONMENT_EXTRACTOR.extract(environment);
        } catch (Exception e) {
            LOGGER.error("读取配置异常异常：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T extractProperties(String configPath, Class<? extends Properties<T>> clazz) {
        // 从文件路径获取配置文件
        File propertiesFile = getPropertiesFile(configPath);
        if (null == propertiesFile) {
            return null;
        } else
            try {
                ConfigurableEnvironment environment = extractYamlProperties(propertiesFile);
                return extractProperties(environment, clazz);
            } catch (Exception e) {
                LOGGER.error("读取配置异常异常：{}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
    }

    /**
     * 从文件中提取环境变量
     *
     * @param propertiesFile 配置文件
     * @return 返回 {@link Environment}
     * @throws IOException 读取配置文件失败后所抛出的流异常
     */
    private static ConfigurableEnvironment extractYamlProperties(File propertiesFile) throws IOException {
        if (propertiesFile == null) {
            return null;
        }
        StandardEnvironment environment = new StandardEnvironment();
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        yamlPropertySourceLoader.load("EnvironmentExtractor", new FileSystemResource(propertiesFile))
                .forEach(e -> {
                    environment.getPropertySources().addFirst(e);
                });
        return environment;
    }

    private static File getPropertiesFile(String configPath) {
        if (Strings.hasText(configPath)) {
            return getPropertiesFileForConfigPath(configPath);
        }
        return null;
    }

    private static File getPropertiesFileForConfigPath(String configPath) {
        File file = new File(configPath);
        if (file.exists() && file.isFile()) {
            return file;
        } else
            return null;
    }

    public static void main(String[] args) {
        CipherProperties cipherProperties = EnvironmentExtractor.extractProperties(
                "/Users/zhangziyao/workspace/project/CodeHarbor/harbor-springframework/harbor-spring-crypto/src/main/resources/bootstrap.yml"
                , CipherProperties.class);
        System.out.println(cipherProperties);
    }
}
