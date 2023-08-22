package com.ziyao.harbor.common.writer;

import com.ziyao.harbor.common.api.IMessage;

/**
 * @author ziyao zhang
 * @since 2023/7/24
 */
public abstract class WebResponseBuilder {

    public static <T> DataResponse<T> ok(T data) {
        return WebResponseBuilder.ok(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE(), data);
    }

    public static <T> DataResponse<T> ok() {
        return new DataResponse<>(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE());
    }

    public static <T> DataResponse<T> ok(Integer state, String message, T data) {
        return new DataResponse<>(state, message, data);
    }

    public static <T> DataResponse<T> failed() {
        return new DataResponse<>(IMessage.FAILED_STATE(), IMessage.FAILED_MESSAGE());
    }

    public static <T> DataResponse<T> failed(Integer state, String message) {
        return new DataResponse<>(state, message);
    }

    public static <T> DataResponse<T> failed(Integer state, String message, T data) {
        return new DataResponse<>(state, message, data);
    }

    public static <T> DataResponse<T> failed(IMessage IMessage, String message) {
        return failed(IMessage.getStatus(), message);
    }

    public static <T> DataResponse<T> failed(IMessage IMessage) {
        return failed(IMessage.getStatus(), IMessage.getMessage());
    }
}
