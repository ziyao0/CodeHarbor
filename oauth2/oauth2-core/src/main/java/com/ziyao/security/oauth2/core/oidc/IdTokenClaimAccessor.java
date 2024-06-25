package com.ziyao.security.oauth2.core.oidc;

import com.ziyao.security.oauth2.core.ClaimAccessor;

import java.time.Instant;
import java.util.List;

/**
 * @author ziyao
 * @since 2024/06/19 14:52:48
 */
public interface IdTokenClaimAccessor extends ClaimAccessor {

    /**
     * 返回发行者标识符 {@code (iss)}.
     *
     * @return 发行标识
     */
    default String getIssuer() {
        return this.getClaimAsString(IdTokenClaimNames.ISS);
    }

    /**
     * 返回主体标识，一般为用户ID {@code (sub)}.
     *
     * @return 主体
     */

    default String getSubject() {
        return this.getClaimAsString(IdTokenClaimNames.SUB);
    }

    /**
     * 返回令牌受众
     */
    default List<String> getAudience() {
        return this.getClaimAsStringList(IdTokenClaimNames.AUD);
    }

    /**
     * 返回令牌过期时间
     */
    default Instant getExpiresAt() {
        return this.getClaimAsInstant(IdTokenClaimNames.EXP);
    }

    /**
     * 返回令牌的颁发时间
     *
     * @return the time at which the ID Token was issued
     */
    default Instant getIssuedAt() {
        return this.getClaimAsInstant(IdTokenClaimNames.IAT);
    }

}
