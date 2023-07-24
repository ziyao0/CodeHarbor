package com.ziyao.cfx.common.writer;

import com.ziyao.cfx.common.api.DataIMessage;
import com.ziyao.cfx.common.api.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.Objects;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public final class DataResponse<T> implements DataIMessage<T> {

    @Serial
    private static final long serialVersionUID = 4683215504590500529L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataResponse.class);

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

    public DataResponse() {
    }

    public DataResponse(IMessage IMessage) {
        Checker.check(IMessage);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
    }

    public DataResponse(IMessage IMessage, T data) {
        Checker.check(IMessage, data);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
        this.data = data;
    }

    public DataResponse(Integer state, String message) {
        Checker.check(state, message);
        this.state = state;
        this.message = message;

    }

    public DataResponse(Integer state, String message, T data) {
        Checker.check(state, message, data);
        this.state = state;
        this.message = message;
        this.data = data;
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
        DataResponse<?> that = (DataResponse<?>) o;
        return Objects.equals(state, that.state) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, message, data);
    }
}
