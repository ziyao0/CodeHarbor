package com.ziyao.security.oauth2.core;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.converter.ClaimConversionService;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author ziyao
 * @since 2024/06/19 11:03:51
 */
public interface ClaimAccessor {

    /**
     * Returns a set of claims that may be used for assertions.
     *
     * @return a {@code Map} of claims
     */
    Map<String, Object> getClaims();

    /**
     * 返回声明中是否有该元素
     *
     * @param claimName claim name
     * @return true 为存在
     */
    default boolean hasClaim(String claimName) {
        if (Strings.hasText(claimName)) {
            return getClaims().containsKey(claimName);
        } else
            return false;
    }

    /**
     * 获取元素
     *
     * @param claimName 元素名称
     * @param <T>       value type
     * @return 返回声明的元素信息
     */
    @SuppressWarnings("unchecked")
    default <T> T getClaim(String claimName) {
        return hasClaim(claimName) ? (T) getClaims().get(claimName) : null;
    }

    default String getClaimAsString(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), String.class) : null;
    }

    default Boolean getClaimAsBoolean(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), Boolean.class) : null;
    }

    default Integer getClaimAsInteger(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), Integer.class) : null;
    }

    default Long getClaimAsLong(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), Long.class) : null;
    }

    default Instant getClaimAsInstant(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), Instant.class) : null;
    }

    @SuppressWarnings("unchecked")
    default List<String> getClaimAsStringList(String claimName) {
        return hasClaim(claimName) ?
                ClaimConversionService.getSharedInstance().convert(getClaim(claimName), List.class) : null;
    }
}
