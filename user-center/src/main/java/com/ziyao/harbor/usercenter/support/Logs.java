package com.ziyao.harbor.usercenter.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public abstract class Logs {


    public static Logger getLogger(Class<?> beanClass) {
        return LoggerFactory.getLogger(beanClass);
    }

    private Logs() {
    }
}
