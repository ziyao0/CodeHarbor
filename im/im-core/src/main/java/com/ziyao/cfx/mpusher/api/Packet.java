package com.ziyao.cfx.mpusher.api;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class Packet implements Serializable {
    @Serial
    private static final long serialVersionUID = -130418831225718544L;

    private Event event;

    private Object msg;

    private Type type;
    /**
     * 当接收人是多个的时候证明需要群发
     */
    private List<String> receivedBys;

    public Packet() {
    }

    public Packet(Event event, Object msg) {
        this.event = event;
        this.msg = msg;
    }

    public Packet(Object msg, String receivedBy) {
        this.msg = msg;
        this.type = Type.UNICAST;
        this.receivedBys = List.of(receivedBy);
    }

    public Packet(Object msg) {
        this.msg = msg;
        this.event = Event.SEND;
        this.type = Type.BROADCAST;
    }

    public Packet(Object msg, List<String> receivedBys) {
        this.msg = msg;
        this.type = Type.UNICAST;
        this.receivedBys = receivedBys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return event == packet.event && Objects.equals(msg, packet.msg) && type == packet.type && Objects.equals(receivedBys, packet.receivedBys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, msg, type, receivedBys);
    }

    public Event getState() {
        return event;
    }

    public void setState(Event event) {
        this.event = event;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getReceivedBys() {
        return receivedBys;
    }

    public void setReceivedBys(List<String> receivedBys) {
        this.receivedBys = receivedBys;
    }

    public enum Type {
        BROADCAST,
        UNICAST,
        ;
    }
}
