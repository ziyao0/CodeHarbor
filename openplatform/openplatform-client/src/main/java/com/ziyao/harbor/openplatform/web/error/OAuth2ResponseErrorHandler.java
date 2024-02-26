package com.ziyao.harbor.openplatform.web.error;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @author ziyao zhang
 * @since 2024/1/10
 */
public class OAuth2ResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(@NonNull ClientHttpResponse response) throws IOException {
        if ((!response.getStatusCode().is4xxClientError() && !response.getStatusCode().is5xxServerError())
                || MediaType.TEXT_HTML.includes(response.getHeaders().getContentType())) {
            super.handleError(response);
        }
    }
}
