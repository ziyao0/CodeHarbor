package com.ziyao.harbor.crypto.core;

import org.springframework.core.env.PropertySource;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public class ObjectPropertySource extends PropertySource<Object> {


    public ObjectPropertySource(String name, Object source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return this.source;
    }

}
