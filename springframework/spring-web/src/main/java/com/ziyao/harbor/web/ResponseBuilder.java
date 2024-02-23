package com.ziyao.harbor.web;

import com.ziyao.harbor.core.error.MessageResponse;
import com.ziyao.harbor.core.error.StatusMessage;

/**
 * @author ziyao zhang
 * @since 2023/7/24
 */
public abstract class ResponseBuilder {

    public static <T> MessageResponse<T> ok(T data) {
        return ResponseBuilder.ok(StatusMessage.SUCCESS_STATE(), StatusMessage.SUCCESS_MESSAGE(), data);
    }

    public static <T> MessageResponse<T> ok() {
        return new MessageResponse<>(StatusMessage.SUCCESS_STATE(), StatusMessage.SUCCESS_MESSAGE());
    }

    public static <T> MessageResponse<T> ok(Integer state, String message, T data) {
        return new MessageResponse<>(state, message, data);
    }

    public static <T> MessageResponse<T> failed() {
        return new MessageResponse<>(StatusMessage.FAILED_STATE(), StatusMessage.FAILED_MESSAGE());
    }

    public static <T> MessageResponse<T> of(Integer state, String message) {
        return new MessageResponse<>(state, message);
    }

    public static <T> MessageResponse<T> of(Integer state, String message, T data) {
        return new MessageResponse<>(state, message, data);
    }

    public static <T> MessageResponse<T> of(StatusMessage StatusMessage, String message) {
        return of(StatusMessage.getStatus(), message);
    }

    public static <T> MessageResponse<T> of(StatusMessage StatusMessage) {
        return of(StatusMessage.getStatus(), StatusMessage.getMessage());
    }

    private ResponseBuilder() {

    }
}
