package com.ziyao.harbor.gateway.factory.chain;

import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import org.springframework.stereotype.Component;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class IPBlacklistHandler extends AbstractSecurityHandler {
    @Override
    public void handle(DefaultAccessToken defaultAccessToken) {
        String ip = defaultAccessToken.getIp();
        if ("127.0.0.1".equals(ip)) {
            throw Exceptions.createIllegalAccessException(ip);
        }
        this.checkedNextHandler(defaultAccessToken);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
