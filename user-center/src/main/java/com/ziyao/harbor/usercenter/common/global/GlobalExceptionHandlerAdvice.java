package com.ziyao.harbor.usercenter.common.global;

import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateException;
import com.ziyao.harbor.web.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author ziyao zhang
 * @since 2023/12/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {


    @ExceptionHandler(value = AuthenticateException.class)
    public StatusMessage exceptionHandler(AuthenticateException e) {
        Throwable causeThrowable = e.getCause();
        if (causeThrowable != null) {
            log.error("发生由其他异常导致的业务异常", causeThrowable);
        }
        return ResponseBuilder.of(e.getStatus(), e.getMessage());
    }
}
