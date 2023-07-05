package com.ziyao.cfx.im.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Packet implements Serializable {
    @Serial
    private static final long serialVersionUID = -130418831225718544L;

    private Event event;

    private Object data;

    private Type type;
    /**
     * 当接收人是多个的时候证明需要群发
     */
    private List<String> receivedBys;

    public Packet() {
    }

    public Packet(Event event, Object data) {
        this.event = event;
        this.data = data;
    }

    public Packet(Object data, String receivedBy) {
        this.data = data;
        this.type = Type.UNICAST;
        this.receivedBys = List.of(receivedBy);
    }

    public Packet(Object data) {
        this.data = data;
        this.event = Event.SEND;
        this.type = Type.BROADCAST;
    }

    public Packet(Object data, List<String> receivedBys) {
        this.data = data;
        this.type = Type.UNICAST;
        this.receivedBys = receivedBys;
    }

    public enum Type {
        BROADCAST,
        UNICAST,
        ;
    }
}
