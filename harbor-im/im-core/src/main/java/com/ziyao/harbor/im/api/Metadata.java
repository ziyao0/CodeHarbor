package com.ziyao.harbor.im.api;


import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class Metadata implements Serializable {

    private static final long serialVersionUID = 2652829910177078728L;

    private int dataLength;

    private Agreement agreement;

    private Packet packet;

    public static Metadata.MetadataBuilder builder() {
        return new Metadata.MetadataBuilder();
    }

    public Metadata() {
    }

    public Metadata(int dataLength, Agreement agreement, Packet packet) {
        this.dataLength = dataLength;
        this.agreement = agreement;
        this.packet = packet;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public Packet getMessage() {
        return packet;
    }

    public void setMessage(Packet packet) {
        this.packet = packet;
    }

    public static class MetadataBuilder {
        private int dataLength;
        private Packet packet;
        private Agreement agreement;

        public MetadataBuilder() {
        }

        public Metadata.MetadataBuilder dataLength(final int dataLength) {
            this.dataLength = dataLength;
            return this;
        }

        public Metadata.MetadataBuilder message(final Packet packet) {
            this.packet = packet;
            return this;
        }

        public Metadata.MetadataBuilder agreement(final Agreement agreement) {
            this.agreement = agreement;
            return this;
        }

        public Metadata build() {
            return new Metadata(this.dataLength, this.agreement, this.packet);
        }
    }
}
