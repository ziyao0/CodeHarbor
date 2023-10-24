package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.core.utils.RegexPool;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.encrypt.CipherUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;

import java.util.*;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DecodeEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String HARBOR_CRYPTO_KEY_NAME = "harbor_crypto";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        environment.getPropertySources().remove(HARBOR_CRYPTO_KEY_NAME);
        Map<String, Object> properties = decrypt(propertySources);
        if (!Collections.isEmpty(properties)) {
            environment.getPropertySources().addFirst(
                    new SystemEnvironmentPropertySource(
                            HARBOR_CRYPTO_KEY_NAME, properties)
            );
        }
    }

    private Map<String, Object> decrypt(MutablePropertySources propertySources) {
        Map<String, Object> properties = merge(propertySources);
        decrypt(properties);
        return new HashMap<>();
    }

    private void decrypt(Map<String, Object> properties) {
        properties.forEach((key, value) -> {
            if (value instanceof Property) {
                properties.put(key, decrypt((Property) value));
            }
        });
    }

    private String decrypt(Property property) {
        if (property.isEncryption()) {
            try {
                TextCipher textCipher = property.getTextCipher();
                return textCipher.decrypt(property.getValue());
            } catch (Exception e) {
                throw new IllegalStateException("配置加密信息被损坏，请检查配置文件数据，key:" + property.getValue(), e);
            }
        }
        return property.getValue();
    }

    private Map<String, Object> merge(MutablePropertySources propertySources) {
        Map<String, Object> properties = new LinkedHashMap<>();
        List<PropertySource<?>> sources = new ArrayList<>();
        for (PropertySource<?> source : propertySources) {
            sources.add(0, source);
        }
        for (PropertySource<?> source : sources) {
            merge(source, properties);
        }
        return properties;
    }

    private void merge(PropertySource<?> source, Map<String, Object> properties) {
        LinkedHashMap<String, Object> otherCollectionProperties = new LinkedHashMap<>();
        boolean sourceHasDecryptedCollection = false;

        EnumerablePropertySource<?> enumerable = (EnumerablePropertySource<?>) source;

        for (int i = 0; i < enumerable.getPropertyNames().length; i++) {
            String key = enumerable.getPropertyNames()[i];
            Object sourceValue = source.getProperty(key);
            if (sourceValue != null) {
                String value = sourceValue.toString();
                Property property = parseProperty(key, value);
                if (property.isEncryption()) {
                    properties.put(key, property);
                    if (RegexPool.COLLECTION_PROPERTY.forName().matcher(key).matches()) {
                        sourceHasDecryptedCollection = true;
                    }
                } else if (RegexPool.COLLECTION_PROPERTY.forName().matcher(key).matches()) {
                    // 存储未加密的属性
                    otherCollectionProperties.put(key, value);
                } else {
                    // 删除未使用的配置
                    properties.remove(key);
                }
            }
            if (sourceHasDecryptedCollection && !Collections.isEmpty(otherCollectionProperties)) {
                properties.putAll(otherCollectionProperties);
            }
        }
    }

    /**
     * 解析配置属性值并对配置属性值进行解析，
     *
     * @param key   key
     * @param value 属性值
     * @return {@link Property}
     */
    private Property parseProperty(String key, String value) {
        Property property = new Property(key, value);

        if (Strings.hasText(value)) {
            if (CipherUtils.isMatchPrefix(value)) {
                // 满足前缀匹配算法
                TextCipher textCipher = CipherUtils.matchingTextCipher(null, value);
                property.setValue(value.substring(textCipher.getAlgorithm().length()));
                property.setAlgorithm(textCipher.getAlgorithm());
            }
        }
        return property;
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
