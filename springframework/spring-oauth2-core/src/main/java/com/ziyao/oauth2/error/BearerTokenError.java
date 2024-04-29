package com.ziyao.oauth2.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@Getter
public class BearerTokenError extends OAuth2Error {

    private static final long serialVersionUID = 2409545955385590286L;
    /**
     * -- GETTER --
     * Return the HTTP status.
     */
    private final HttpStatus httpStatus;

    /**
     * -- GETTER --
     * Return the scope.
     */
    private final String scope;

    /**
     * Create a {@code BearerTokenError} using the provided parameters
     *
     * @param errorCode  the error code
     * @param httpStatus the HTTP status
     */
    public BearerTokenError(String errorCode, HttpStatus httpStatus, String description, String errorUri) {
        this(errorCode, httpStatus, description, errorUri, null);
    }

    /**
     * Create a {@code BearerTokenError} using the provided parameters
     *
     * @param errorCode   the error code
     * @param httpStatus  the HTTP status
     * @param description the description
     * @param errorUri    the URI
     * @param scope       the scope
     */
    public BearerTokenError(String errorCode, HttpStatus httpStatus, String description, String errorUri,
                            String scope) {
        super(errorCode, description, errorUri);
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        Assert.isTrue(isDescriptionValid(description),
                "description contains invalid ASCII characters, it must conform to RFC 6750");
        Assert.isTrue(isErrorCodeValid(errorCode),
                "errorCode contains invalid ASCII characters, it must conform to RFC 6750");
        Assert.isTrue(isErrorUriValid(errorUri),
                "errorUri contains invalid ASCII characters, it must conform to RFC 6750");
        Assert.isTrue(isScopeValid(scope), "scope contains invalid ASCII characters, it must conform to RFC 6750");
        this.httpStatus = httpStatus;
        this.scope = scope;
    }

    private static boolean isDescriptionValid(String description) {
        // @formatter:off
        return description == null || description.chars().allMatch((c) ->
                withinTheRangeOf(c, 0x20, 0x21) ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E));
        // @formatter:on
    }

    private static boolean isErrorCodeValid(String errorCode) {
        // @formatter:off
        return errorCode.chars().allMatch((c) ->
                withinTheRangeOf(c, 0x20, 0x21) ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E));
        // @formatter:on
    }

    private static boolean isErrorUriValid(String errorUri) {
        return errorUri == null || errorUri.chars()
                .allMatch((c) -> c == 0x21 || withinTheRangeOf(c, 0x23, 0x5B) || withinTheRangeOf(c, 0x5D, 0x7E));
    }

    private static boolean isScopeValid(String scope) {
        // @formatter:off
        return scope == null || scope.chars().allMatch((c) ->
                withinTheRangeOf(c, 0x20, 0x21) ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E));
        // @formatter:on
    }

    private static boolean withinTheRangeOf(int c, int min, int max) {
        return c >= min && c <= max;
    }
}
