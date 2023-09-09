package com.ziyao.harbor.gateway.core;

import com.alibaba.fastjson2.JSON;
import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.core.error.IMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Component
public class AccessFailureHandler implements FailureHandler {
    @Override
    public DataBuffer onFailureResume(ServerWebExchange exchange, Throwable throwable) {
        IMessage iMessage = Errors.INTERNAL_SERVER_ERROR;
//        if (throwable instanceof UnauthorizedException) {
//            iMessage = IMessage.getInstance(Errors.E_401, throwable.getMessage());
//        } else {
//            log.error("认证异常：{}", throwable.getMessage(), throwable);
//        }

        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(iMessage.getStatus()));
        return exchange.getResponse()
                .bufferFactory()
                .wrap(JSON.toJSONString(iMessage).getBytes());
    }
}
