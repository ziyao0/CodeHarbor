package com.ziyao.harbor.web.response;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2023/8/25
 */
public class MessageResponse<T> implements IMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageResponse.class);
    @Serial
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

    public MessageResponse(IMessage IMessage) {
        Checker.check(IMessage);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
    }

    public MessageResponse(IMessage IMessage, T data) {
        Checker.check(IMessage, data);
        this.state = IMessage.getStatus();
        this.message = IMessage.getMessage();
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

        public static void check(IMessage IMessage) {
            check(IMessage.getStatus(), IMessage.getMessage());
        }

        public static void check(IMessage IMessage, Object data) {
            check(IMessage.getStatus(), IMessage.getMessage(), data);
        }
    }
}