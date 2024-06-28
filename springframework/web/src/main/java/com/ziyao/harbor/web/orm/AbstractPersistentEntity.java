package com.ziyao.harbor.web.orm;

import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/06/28 15:19:15
 */
@Setter
public abstract class AbstractPersistentEntity<U> {

    /**
     * 创建人ID
     */
    private U createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新人ID
     */
    private U updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public Optional<U> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public Optional<LocalDateTime> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public Optional<U> getUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }

    public Optional<LocalDateTime> getUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

}
