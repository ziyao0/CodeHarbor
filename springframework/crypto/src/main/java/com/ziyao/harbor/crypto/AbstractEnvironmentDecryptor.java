package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.core.utils.RegexPool;
import com.ziyao.harbor.crypto.core.CryptoContext;
import com.ziyao.harbor.crypto.utils.ConstantPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public abstract class AbstractEnvironmentDecryptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEnvironmentDecryptor.class);
    protected static final String CIPHER_PROPERTY_SOURCE_NAME = ConstantPool.CIPHER_PROPERTY_SOURCE_NAME;
    protected static final String CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME = ConstantPool.CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME;


    protected Map<String, Object> decrypt(CryptoContext context, MutablePropertySources propertySources) {
        Map<String, Object> properties = merge(context, propertySources);
        decrypt(properties, context.isFailOnError());
        return properties;
    }

    protected void decrypt(Map<String, Object> properties, boolean failOnError) {
        properties.forEach((key, value) -> {
            if (value instanceof Property) {
                properties.put(key, decrypt((Property) value, failOnError));
            }
        });
    }

    protected String decrypt(Property property, boolean failOnError) {
        if (property.isEncryption()) {
            try {
                TextCipher textCipher = property.getTextCipher();
                return textCipher.decrypt(String.valueOf(property.getValue()));
            } catch (Exception e) {
                String decryptFail = "配置加密信息被损坏，请检查配置文件数据，algorithm:,[" + property.getAlgorithm() + "],key:[" + property.getKey() + "],value:[" + property.getValue() + "]";
                if (failOnError)
                    throw new IllegalStateException(decryptFail, e);
                else
                    LOGGER.error(decryptFail);
            }
        }
        return String.valueOf(property.getValue());
    }


    protected Map<String, Object> merge(CryptoContext context, MutablePropertySources propertySources) {
        Map<String, Object> properties = new LinkedHashMap<>();
        List<PropertySource<?>> sources = new ArrayList<>();
        for (PropertySource<?> source : propertySources) {
            sources.add(0, source);
        }
        for (PropertySource<?> source : sources) {
            merge(context, source, properties);
        }
        return properties;
    }

    protected Map<String, Object> merge(CryptoContext context,
                                        PropertySource<?> source) {
        Map<String, Object> properties = new LinkedHashMap<>();
        merge(context, source, properties);
        return properties;
    }

    protected void merge(CryptoContext context, PropertySource<?> source, Map<String, Object> properties) {

        if (source instanceof CompositePropertySource) {

            List<PropertySource<?>> sources = new ArrayList<>(
                    ((CompositePropertySource) source).getPropertySources());
            java.util.Collections.reverse(sources);

            for (PropertySource<?> nested : sources) {
                merge(context, nested, properties);
            }

        } else if (source instanceof EnumerablePropertySource<?> enumerable) {

            LinkedHashMap<String, Object> otherCollectionProperties = new LinkedHashMap<>();
            boolean sourceHasDecryptedCollection = false;

            PropertyResolver propertyResolver = context.getPropertyResolver();
            for (int i = 0; i < enumerable.getPropertyNames().length; i++) {
                String key = enumerable.getPropertyNames()[i];
                Object sourceValue = source.getProperty(key);
                if (sourceValue != null) {
                    String value = sourceValue.toString();
                    Property property = propertyResolver.getProperty(key, value);
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
    }
}
