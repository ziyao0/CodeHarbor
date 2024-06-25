package com.ziyao.security.oauth2.core.settings;

import lombok.Getter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Getter
public class OAuth2TokenFormat implements Serializable {

    /**
     * -- GETTER --
     * Returns the value of the token format.
     */
    private final String value;

    private static final long serialVersionUID = -5595881899154576597L;

    /**
     * Self-contained tokens use a protected, time-limited data structure that contains
     * token metadata and claims of the user and/or client. JSON Web Token (JWT) is a
     * widely used format.
     */
    public static final OAuth2TokenFormat SELF_CONTAINED = new OAuth2TokenFormat("self-contained");

    /**
     * Reference (opaque) tokens are unique identifiers that serve as a reference to the
     * token metadata and claims of the user and/or client, stored at the provider.
     */
    public static final OAuth2TokenFormat REFERENCE = new OAuth2TokenFormat("reference");

    /**
     * Constructs an {@code OAuth2TokenFormat} using the provided value.
     *
     * @param value the value of the token format
     */
    public OAuth2TokenFormat(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2TokenFormat that = (OAuth2TokenFormat) o;
        return Objects.equals(value, that.value);
    }

}
