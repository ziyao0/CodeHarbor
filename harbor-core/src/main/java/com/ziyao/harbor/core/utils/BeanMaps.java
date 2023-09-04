package com.ziyao.harbor.core.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
public abstract class BeanMaps {

    private BeanMaps() {
    }

    public static Map<String, Object> tomap(Object object) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        return new HashMap<>(jsonObject);
    }

}
