package com.cfx.common.writer;

import com.cfx.common.api.DataMessageDetails;
import com.cfx.common.api.MessageDetails;

import java.util.Objects;

/**
 * @author Eason
 * @date 2023/4/21
 */
public final class ApiResponse<T> implements DataMessageDetails<T> {

    private static final long serialVersionUID = 4683215504590500529L;

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

    public ApiResponse(MessageDetails messageDetails) {
        Checker.check(messageDetails);
        this.state = messageDetails.getStatus();
        this.message = messageDetails.getMessage();
    }

    public ApiResponse(MessageDetails messageDetails, T data) {
        Checker.check(messageDetails, data);
        this.state = messageDetails.getStatus();
        this.message = messageDetails.getMessage();
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

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(MessageDetails.SUCCESS_STATE(), MessageDetails.SUCCESS_MESSAGE(), data);
    }

    public static <T> ApiResponse<T> success(Integer state, String message, T data) {
        return new ApiResponse<>(state, message, data);
    }

    public static <T> ApiResponse<T> failed() {
        return new ApiResponse<>(MessageDetails.FAILED_STATE(), MessageDetails.FAILED_MESSAGE());
    }

    public static <T> ApiResponse<T> failed(Integer state, String message) {
        return new ApiResponse<>(state, message);
    }

    public static <T> ApiResponse<T> failed(MessageDetails messageDetails, String message) {
        return failed(messageDetails.getStatus(), message);
    }

    public static <T> ApiResponse<T> failed(MessageDetails messageDetails) {
        return failed(messageDetails.getStatus(), messageDetails.getMessage());
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
                throw new IllegalArgumentException("data cannot be empty.");
            }
        }

        public static void check(MessageDetails messageDetails) {
            check(messageDetails.getStatus(), messageDetails.getMessage());
        }

        public static void check(MessageDetails messageDetails, Object data) {
            check(messageDetails.getStatus(), messageDetails.getMessage(), data);
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
