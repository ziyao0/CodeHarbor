package com.ziyao.harbor.openplatform.web.client;

import com.ziyao.harbor.openplatform.web.error.OpenPlatformResponseErrorHandler;
import com.ziyao.harbor.openplatform.web.http.SslHttpRequestFactory;
import com.ziyao.harbor.openplatform.web.http.converter.HttpMessageConverters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/1/10
 */
public class RestClientTemplate extends RestTemplate {

    public RestClientTemplate() {
        this.setInterceptors(new ArrayList<>());
        // 错误信息处理
        this.setErrorHandler(new OpenPlatformResponseErrorHandler());
        // https 情况下默认不校验服务端证书(不安全).
        this.setRequestFactory(new SslHttpRequestFactory());
        // 默认情况下将 HttpMessageConvert 排序, 优先使用支持 json 的 HttpMessageConvert.
        this.setMessageConverters(HttpMessageConverters.sortHttpMessageConverts(getMessageConverters()));
    }


    @Override
    public void setInterceptors(@NonNull List<ClientHttpRequestInterceptor> interceptors) {
        super.setInterceptors(interceptors);
    }

    @Override
    public <T> @NonNull RequestCallback httpEntityCallback(@Nullable Object requestBody) {
        return super.httpEntityCallback(wrapBodyToHttpEntity(requestBody));
    }

    @Override
    public <T> @NonNull RequestCallback httpEntityCallback(@Nullable Object requestBody, @NonNull Type responseType) {
        return super.httpEntityCallback(wrapBodyToHttpEntity(requestBody), responseType);
    }

    @Override
    protected @NonNull ClientHttpRequest createRequest(@NonNull URI url, @NonNull HttpMethod method) throws IOException {
        return super.createRequest(url, method);
    }

    private static HttpEntity<Object> wrapBodyToHttpEntity(Object requestBody) {
        HttpEntity<?> requestEntity = wrapBodyToHttpEntityI(requestBody);
        HttpHeaders tempHeaders = cloneHttpHeaders(requestEntity.getHeaders());
        MediaType contentType = tempHeaders.getContentType();
        if (contentType == null) {
            tempHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        return new HttpEntity<>(requestEntity.getBody(), tempHeaders);
    }

    /**
     * 克隆请求头信息.
     *
     * @param headers 请求头信息
     * @return 克隆的请求头信息
     */
    public static HttpHeaders cloneHttpHeaders(HttpHeaders headers) {
        HttpHeaders clone = new HttpHeaders();
        if (headers != null) {
            clone.putAll(headers);
        }
        return clone;
    }

    /**
     * 将 body 对象包装为 {@link HttpEntity}.
     *
     * @param requestBody 请求体对象
     * @return 包含请求体对象的 HttpEntity
     */
    private static HttpEntity<?> wrapBodyToHttpEntityI(Object requestBody) {
        if (requestBody instanceof HttpEntity) {
            return (HttpEntity<?>) requestBody;
        } else {
            return new HttpEntity<>(requestBody);
        }
    }
}
