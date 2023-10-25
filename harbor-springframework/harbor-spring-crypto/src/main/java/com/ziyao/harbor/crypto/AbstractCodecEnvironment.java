package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.core.utils.RegexPool;
import com.ziyao.harbor.crypto.core.CipherContext;
import com.ziyao.harbor.crypto.core.Property;
import com.ziyao.harbor.crypto.core.PropertyResolver;
import com.ziyao.harbor.crypto.utils.ConstantPool;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.*;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public abstract class AbstractCodecEnvironment {


    protected static final String CIPHER_PROPERTY_SOURCE_NAME = ConstantPool.CIPHER_PROPERTY_SOURCE_NAME;
    protected static final String CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME = ConstantPool.CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME;


    protected Map<String, Object> decrypt(CipherContext context, MutablePropertySources propertySources) {
        Map<String, Object> properties = merge(context, propertySources);
        decrypt(properties);
        return new HashMap<>();
    }

    protected void decrypt(Map<String, Object> properties) {
        properties.forEach((key, value) -> {
            if (value instanceof Property) {
                properties.put(key, decrypt((Property) value));
            }
        });
    }

    protected String decrypt(Property property) {
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


    protected Map<String, Object> merge(CipherContext context, MutablePropertySources propertySources) {
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

    protected Map<String, Object> merge(CipherContext context,
                                        PropertySource<?> source) {
        Map<String, Object> properties = new LinkedHashMap<>();
        merge(context, source, properties);
        return properties;
    }

    protected void merge(CipherContext context, PropertySource<?> source, Map<String, Object> properties) {

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
