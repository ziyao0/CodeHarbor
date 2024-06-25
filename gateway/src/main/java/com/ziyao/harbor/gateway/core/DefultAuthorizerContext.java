package com.ziyao.harbor.gateway.core;

import lombok.Data;

import java.util.Map;

/**
 * @author ziyao
 * @since 2024/06/01 23:42:01
 */
@Data
public class DefultAuthorizerContext implements AuthorizerContext{


    private Map<String, Object> attributes;
}
