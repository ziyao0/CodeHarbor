package com.ziyao.cfx.common.dto;

import org.springframework.beans.BeanUtils;

/**
 * @author ziyao zhang
 * @since 2023/5/5
 */
public interface EntityDTO<T> {

    default Long getId() {
        return null;
    }

    /**
     * 获取实体属性
     */
    T getEntity();

    default T getInstance() {
        T entity = getEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
