package com.ziyao.harbor.web;

import com.ziyao.harbor.core.error.IMessage;
import com.ziyao.harbor.core.error.MessageResponse;

/**
 * @author ziyao zhang
 * @since 2023/7/24
 */
public abstract class ResponseBuilder {

    public static <T> MessageResponse<T> ok(T data) {
        return ResponseBuilder.ok(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE(), data);
    }

    public static <T> MessageResponse<T> ok() {
        return new MessageResponse<>(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE());
    }

    public static <T> MessageResponse<T> ok(Integer state, String message, T data) {
        return new MessageResponse<>(state, message, data);
    }

    public static <T> MessageResponse<T> failed() {
        return new MessageResponse<>(IMessage.FAILED_STATE(), IMessage.FAILED_MESSAGE());
    }

    public static <T> MessageResponse<T> failed(Integer state, String message) {
        return new MessageResponse<>(state, message);
    }

    public static <T> MessageResponse<T> failed(Integer state, String message, T data) {
        return new MessageResponse<>(state, message, data);
    }

    public static <T> MessageResponse<T> failed(IMessage IMessage, String message) {
        return failed(IMessage.getStatus(), message);
    }

    public static <T> MessageResponse<T> failed(IMessage IMessage) {
        return failed(IMessage.getStatus(), IMessage.getMessage());
    }
}
