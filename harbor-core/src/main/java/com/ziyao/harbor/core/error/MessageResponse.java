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
    private transient T data;

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

    public MessageResponse(StatusMessage statusMessage) {
        Checker.checked(statusMessage);
        this.state = statusMessage.getStatus();
        this.message = statusMessage.getMessage();
    }

    public MessageResponse(StatusMessage statusMessage, T data) {
        Checker.checked(statusMessage, data);
        this.state = statusMessage.getStatus();
        this.message = statusMessage.getMessage();
        this.data = data;
    }

    public MessageResponse(Integer state, String message) {
        Checker.checked(state, message);
        this.state = state;
        this.message = message;

    }

    public MessageResponse(Integer state, String message, T data) {
        Checker.checked(state, message, data);
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public abstract static class Checker {

        private Checker() {
        }

        public static void checked(Integer state) {
            if (null == state) {
                throw new IllegalArgumentException("state cannot be empty.");
            }
        }

        public static void checked(Integer state, String message) {

            checked(state);

            if (null == message) {
                throw new IllegalArgumentException("message cannot be empty.");
            }
        }

        public static void checked(Integer state, String message, Object data) {

            checked(state, message);

            if (null == data) {
                LOGGER.debug("response data cannot be empty.");
            }
        }

        public static void checked(StatusMessage statusMessage) {
            checked(statusMessage.getStatus(), statusMessage.getMessage());
        }

        public static void checked(StatusMessage statusMessage, Object data) {
            checked(statusMessage.getStatus(), statusMessage.getMessage(), data);
        }
    }
}
