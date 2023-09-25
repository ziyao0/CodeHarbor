package com.ziyao.harbor.gateway.core.factory.chain;

import com.ziyao.harbor.core.Ordered;
import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import org.springframework.stereotype.Component;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class IPBlacklistHandler extends AbstractSecurityHandler {
    @Override
    public void handle(AccessControl accessControl) {
        String ip = accessControl.getIp();
        if ("127.0.0.1".equals(ip)) {
            throw Exceptions.createIllegalAccessException(ip);
        }
        this.checkedNextHandler(accessControl);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
