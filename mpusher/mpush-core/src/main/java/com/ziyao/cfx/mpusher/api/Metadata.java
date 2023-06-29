package com.ziyao.cfx.mpusher.api;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class Metadata implements Serializable {
    @Serial
    private static final long serialVersionUID = 2652829910177078728L;

    private int dataLength;

    private Agreement agreement;

    private Message message;

    public static Metadata.MetadataBuilder builder() {
        return new Metadata.MetadataBuilder();
    }

    public Metadata() {
    }

    public Metadata(int dataLength, Agreement agreement, Message message) {
        this.dataLength = dataLength;
        this.agreement = agreement;
        this.message = message;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class MetadataBuilder {
        private int dataLength;
        private Message message;
        private Agreement agreement;

        public MetadataBuilder() {
        }

        public Metadata.MetadataBuilder dataLength(final int dataLength) {
            this.dataLength = dataLength;
            return this;
        }

        public Metadata.MetadataBuilder message(final Message message) {
            this.message = message;
            return this;
        }

        public Metadata.MetadataBuilder agreement(final Agreement agreement) {
            this.agreement = agreement;
            return this;
        }

        public Metadata build() {
            return new Metadata(this.dataLength, this.agreement, this.message);
        }
    }
}
