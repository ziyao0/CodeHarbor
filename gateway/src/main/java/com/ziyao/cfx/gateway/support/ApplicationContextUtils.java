package com.ziyao.cfx.gateway.support;

import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author ziyao zhang
 * @since 2023/5/17
 */
public abstract class ApplicationContextUtils {


    private static ApplicationContext APPLICATION_CONTEXT;


    /**
     * Get bean of beanName
     *
     * @param beanName beanName
     * @param clazz    The corresponding class of the bean
     * @param <T>      Bean type obtained
     * @return Return bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(beanName, clazz);
    }

    /**
     * Obtain the corresponding bean through class
     *
     * @param clazz The corresponding class of the bean
     * @param <T>   Bean type obtained
     * @return Return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(clazz);
    }

    /**
     * Obtain the corresponding bean list through class
     *
     * @param clazz The corresponding class of the bean
     * @param <T>   Bean type obtained
     * @return Return bean list
     */
    public static <T> List<T> getBeansOfType(Class<T> clazz) {
        Map<String, T> beansOfType = APPLICATION_CONTEXT.getBeansOfType(clazz);
        if (ObjectUtils.isEmpty(beansOfType)) {
            return Collections.emptyList();
        }
        Collection<T> collection = beansOfType.values();
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(collection);
    }

    /**
     * Obtain the corresponding bean map through class
     *
     * @param clazz The corresponding class of the bean
     * @param <T>   Bean type obtained
     * @return Return bean map
     */
    public static <T> Map<String, T> getBeansMapOfType(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBeansOfType(clazz);
    }

    /**
     * get spring application context
     *
     * @return {@link ApplicationContext}
     */

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }


    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.APPLICATION_CONTEXT = applicationContext;
    }
}
