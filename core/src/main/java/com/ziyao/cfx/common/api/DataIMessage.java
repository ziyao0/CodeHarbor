package com.ziyao.cfx.common.api;

import java.util.Objects;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public interface DataIMessage<T> extends IMessage {

    T getData();

    default boolean isSuccessful() {
        return Objects.equals(getStatus(), IMessage.SUCCESS_STATE());
    }


    static <T> DataIMessage<T> getInstance(int state, String message, T data) {
        return new DataIMessage<T>() {
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

    static <T> DataIMessage<T> getInstance(int state, String message) {
        return getInstance(state, message, null);
    }

    static <T> DataIMessage<T> getSuccessInstance(T data) {
        return getInstance(IMessage.SUCCESS_STATE(), IMessage.SUCCESS_MESSAGE(), data);
    }
}
