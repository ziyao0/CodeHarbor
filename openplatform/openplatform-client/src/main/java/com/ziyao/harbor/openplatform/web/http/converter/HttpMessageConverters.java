package com.ziyao.harbor.openplatform.web.http.converter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2024/1/10
 */
public abstract class HttpMessageConverters {

    private static final List<String> CONVERTER_CLASS_LIST = new ArrayList<>(8);
    private static final List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();

    static {
        ClassLoader classLoader = RestTemplate.class.getClassLoader();

        if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)
                && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader)) {
            httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        }
        if (ClassUtils.isPresent("com.google.gson.Gson", classLoader)) {
            httpMessageConverters.add(new GsonHttpMessageConverter());
        }
        if (ClassUtils.isPresent("javax.json.bind.Jsonb", classLoader)) {
            httpMessageConverters.add(new JsonbHttpMessageConverter());
        }
        if (ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader)) {
            httpMessageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        }
        if (ClassUtils.isPresent("javax.xml.bind.Binder", classLoader)) {
            httpMessageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader)) {
            httpMessageConverters.add(new MappingJackson2SmileHttpMessageConverter());
        }
        if (ClassUtils.isPresent("com.fasterxml.jackson.dataformat.cbor.CBORFactory", classLoader)) {
            httpMessageConverters.add(new MappingJackson2CborHttpMessageConverter());
        }

        httpMessageConverters.add(new ByteArrayHttpMessageConverter());
        httpMessageConverters.add(new StringHttpMessageConverter());
        httpMessageConverters.add(new AllEncompassingFormHttpMessageConverter());


        CONVERTER_CLASS_LIST.add("org.springframework.http.converter.json.MappingJackson2HttpMessageConverter");
        CONVERTER_CLASS_LIST.add("org.springframework.http.converter.json.GsonHttpMessageConverter");
        CONVERTER_CLASS_LIST.add("org.springframework.http.converter.json.JsonbHttpMessageConverter");
    }

    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    @SuppressWarnings("unchecked")
    public static void writeStringObjectMap(Map<String, ?> parameters, HttpOutputMessage outputMessage)
            throws HttpMessageNotWritableException, IOException {
        if (httpMessageConverters.isEmpty()) {
            throw new HttpMessageNotWritableException(
                    "Could not found HttpMessageConverter implementation to write outputMessage");
        }
        MediaType contentType = getContentTypeOrDefault(outputMessage.getHeaders());
        for (HttpMessageConverter<?> httpMessageConverter : httpMessageConverters) {
            if (httpMessageConverter instanceof GenericHttpMessageConverter) {
                GenericHttpMessageConverter<Object> genericConverter = (GenericHttpMessageConverter<Object>) httpMessageConverter;
                if (genericConverter.canWrite(STRING_OBJECT_MAP.getType(), Map.class, contentType)) {
                    genericConverter.write(parameters, STRING_OBJECT_MAP.getType(), contentType, outputMessage);
                }
            } else if (httpMessageConverter.canWrite(Map.class, contentType)) {
                ((HttpMessageConverter<Object>) httpMessageConverter).write(parameters, contentType, outputMessage);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> readAsStringObjectMap(HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        if (httpMessageConverters.isEmpty()) {
            throw new HttpMessageNotReadableException(
                    "Could not found HttpMessageConverter implementation to read inputMessage", inputMessage);
        }
        MediaType contentType = getContentTypeOrDefault(inputMessage.getHeaders());
        for (HttpMessageConverter<?> httpMessageConverter : httpMessageConverters) {
            if (httpMessageConverter instanceof GenericHttpMessageConverter) {
                GenericHttpMessageConverter<Object> genericConverter = (GenericHttpMessageConverter<Object>) httpMessageConverter;
                if (genericConverter.canRead(STRING_OBJECT_MAP.getType(), null, contentType)) {
                    return (Map<String, Object>) genericConverter.read(STRING_OBJECT_MAP.getType(), null, inputMessage);
                }
            } else if (httpMessageConverter.canRead(Map.class, contentType)) {
                Object data = ((HttpMessageConverter<Object>) httpMessageConverter).read(Map.class, inputMessage);
                if (data instanceof Map) {
                    return (Map<String, Object>) data;
                } else {
                    throw new HttpMessageNotReadableException(
                            httpMessageConverter.getClass().getSimpleName() + " unable to read inputMessage to Map",
                            inputMessage);
                }
            }
        }
        throw new HttpMessageNotReadableException("Could not found HttpMessageConverter support read inputMessage",
                inputMessage);
    }

    public static List<HttpMessageConverter<?>> sortHttpMessageConverts(
            List<HttpMessageConverter<?>> messageConverters) {
        if (messageConverters == null || messageConverters.isEmpty()) {
            return messageConverters;
        }
        Map<String, HttpMessageConverter<?>> mapping = messageConverters.stream()
                .collect(Collectors.toMap(e -> e.getClass().getName(), e -> e));
        List<HttpMessageConverter<?>> sorted = new ArrayList<>(messageConverters.size());
        for (String name : CONVERTER_CLASS_LIST) {
            if (mapping.containsKey(name)) {
                sorted.add(mapping.get(name));
                mapping.remove(name);
            }
        }
        sorted.addAll(mapping.values());
        return sorted;
    }

    private static MediaType getContentTypeOrDefault(HttpHeaders headers) {
        if (headers == null || headers.getContentType() == null) {
            return MediaType.APPLICATION_JSON;
        } else {
            return headers.getContentType();
        }
    }
}
