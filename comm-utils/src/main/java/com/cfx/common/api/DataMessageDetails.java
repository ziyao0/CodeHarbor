package com.cfx.common.api;

import com.cfx.common.exception.ServiceException;

import java.util.Objects;

/**
 * @author Eason
 * @date 2023/4/21
 */
public interface DataMessageDetails<T> extends MessageDetails {

    T getData();

    default boolean isSuccessful() {
        return Objects.equals(getStatus(), MessageDetails.SUCCESS_STATE());
    }

    default boolean isFailed() {
        return !isSuccessful();
    }

    default T getSuccessfulData() {
        if (isSuccessful()) {
            return getData();
        }
        throw new ServiceException(this);
    }

    static <T> DataMessageDetails<T> getInstance(int state, String message, T data) {
        return new DataMessageDetails<T>() {
            private static final long serialVersionUID = 60363046931488050L;

            @Override
            public T getData() {
                return data;
            }

            @Override
            public Integer getStatus() {
                return state;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    static <T> DataMessageDetails<T> getSuccessInstance(T data) {
        return getInstance(MessageDetails.SUCCESS_STATE(), MessageDetails.SUCCESS_MESSAGE(), data);
    }
}
