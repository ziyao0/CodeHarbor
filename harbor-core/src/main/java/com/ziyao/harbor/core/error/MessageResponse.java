package com.ziyao.harbor.core.error;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ziyao zhang
 * @since 2023/8/25
 */
public class MessageResponse<T> implements StatusMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageResponse.class);
    private static final long serialVersionUID = 7273085408208781818L;

    private Integer state;

    private String message;
    @Getter
    private T data;

    @Override
    public Integer getStatus() {
        return this.state;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public MessageResponse() {
    }

    public MessageResponse(StatusMessage StatusMessage) {
        Checker.check(StatusMessage);
        this.state = StatusMessage.getStatus();
        this.message = StatusMessage.getMessage();
    }

    public MessageResponse(StatusMessage StatusMessage, T data) {
        Checker.check(StatusMessage, data);
        this.state = StatusMessage.getStatus();
        this.message = StatusMessage.getMessage();
        this.data = data;
    }

    public MessageResponse(Integer state, String message) {
        Checker.check(state, message);
        this.state = state;
        this.message = message;

    }

    public MessageResponse(Integer state, String message, T data) {
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

        public static void check(StatusMessage StatusMessage) {
            check(StatusMessage.getStatus(), StatusMessage.getMessage());
        }

        public static void check(StatusMessage StatusMessage, Object data) {
            check(StatusMessage.getStatus(), StatusMessage.getMessage(), data);
        }
    }
}
