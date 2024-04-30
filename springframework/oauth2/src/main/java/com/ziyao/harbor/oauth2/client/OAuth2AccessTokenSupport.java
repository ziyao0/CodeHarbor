package com.ziyao.harbor.oauth2.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public abstract class OAuth2AccessTokenSupport {
    protected final Log logger = LogFactory.getLog(getClass());

    private static final FormHttpMessageConverter FORM_MESSAGE_CONVERTER = new FormHttpMessageConverter();

    private RestOperations restTemplate;

    private List<HttpMessageConverter<?>> messageConverters;
}
