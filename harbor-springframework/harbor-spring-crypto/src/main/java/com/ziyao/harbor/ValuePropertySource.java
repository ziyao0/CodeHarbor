package com.ziyao.harbor;

import org.springframework.core.env.PropertySource;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public class ValuePropertySource extends PropertySource<String> {


    public ValuePropertySource(String name, String source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return this.source;
    }

}
