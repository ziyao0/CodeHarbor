package com.cfx.common.writer;

import com.cfx.common.api.DataIMessage;
import com.cfx.common.api.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public final class ApiResponse<T> implements DataIMessage<T> {

    private static final long serialVersionUID = 4683215504590500529L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResponse.class);

    private Integer state;

    private String message;

    private T data;

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public Integer getStatus() {
        return this.state;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public ApiResponse() {
    }

    public ApiResponse(IMessage IMessage) {
        Checker.check(IMessage);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
    }

    public ApiResponse(IMessage IMessage, T data) {
        Checker.check(IMessage, data);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
        this.data = data;
    }

    public ApiResponse(Integer state, String message) {
        Checker.check(state, message);
        this.state = state;
        this.message = message;

    }

    public ApiResponse(Integer state, String message, T data) {
        Checker.check(state, message, data);
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.ok(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE(), data);
    }

    public static <T> ApiResponse<T> ok(Integer state, String message, T data) {
        return new ApiResponse<>(state, message, data);
    }

    public static <T> ApiResponse<T> failed() {
        return new ApiResponse<>(IMessage.FAILED_STATE(), IMessage.FAILED_MESSAGE());
    }

    public static <T> ApiResponse<T> failed(Integer state, String message) {
        return new ApiResponse<>(state, message);
    }

    public static <T> ApiResponse<T> failed(Integer state, String message, T data) {
        return new ApiResponse<>(state, message, data);
    }

    public static <T> ApiResponse<T> failed(IMessage IMessage, String message) {
        return failed(IMessage.getStatus(), message);
    }

    public static <T> ApiResponse<T> failed(IMessage IMessage) {
        return failed(IMessage.getStatus(), IMessage.getMessage());
    }


    protected abstract static class Checker {

        public static void check(Integer state) {
            if (null == state) {
                throw new IllegalArgumentException("state cannot be empty.");
            }
        }

        public static void check(Integer state, String message) {

            check(state);

            if (null == message) {
                throw new IllegalArgumentException("message cannot be empty.");
            }
        }

        public static void check(Integer state, String message, Object data) {

            check(state, message);

            if (null == data) {
                LOGGER.debug("response data cannot be empty.");
            }
        }

        public static void check(IMessage IMessage) {
            check(IMessage.getStatus(), IMessage.getMessage());
        }

        public static void check(IMessage IMessage, Object data) {
            check(IMessage.getStatus(), IMessage.getMessage(), data);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return Objects.equals(state, that.state) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, message, data);
    }
}
