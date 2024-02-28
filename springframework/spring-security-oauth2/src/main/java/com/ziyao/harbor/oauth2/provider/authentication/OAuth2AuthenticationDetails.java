package com.ziyao.harbor.oauth2.provider.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class OAuth2AuthenticationDetails implements Serializable {

    private static final long serialVersionUID = 8604154131270675677L;
    public static final String ACCESS_TOKEN_VALUE = OAuth2AuthenticationDetails.class.getSimpleName() + ".ACCESS_TOKEN_VALUE";

    public static final String ACCESS_TOKEN_TYPE = OAuth2AuthenticationDetails.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    /**
     * -- GETTER --
     * Indicates the TCP/IP address the authentication request was received from.
     *
     * @code the address
     */
    @Getter
    private final String remoteAddress;

    /**
     * -- GETTER --
     * Indicates the <code>HttpSession</code> id the authentication request was received from.
     *
     * @code the session ID
     */
    @Getter
    private final String sessionId;

    /**
     * -- GETTER --
     * The access token value used to authenticate the request (normally in an authorization header).
     *
     * @code the tokenValue used to authenticate the request
     */
    @Getter
    private final String tokenValue;

    /**
     * -- GETTER --
     * The access token type used to authenticate the request (normally in an authorization header).
     *
     * @code the tokenType used to authenticate the request if known
     */
    @Getter
    private final String tokenType;

    private final String display;

    /**
     * -- GETTER --
     * The authentication details obtained by decoding the access token
     * if available.
     * <p>
     * <p>
     * -- SETTER --
     * The authentication details obtained by decoding the access token
     * if available.
     * <p>
     * the decodedDetails if available (default null)
     * decodedDetails the decodedDetails to set
     */
    @Setter
    @Getter
    private Object decodedDetails;


    /**
     * Records the access token value and remote address and will also set the session Id if a session already exists
     * (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public OAuth2AuthenticationDetails(HttpServletRequest request) {
        this.tokenValue = (String) request.getAttribute(ACCESS_TOKEN_VALUE);
        this.tokenType = (String) request.getAttribute(ACCESS_TOKEN_TYPE);
        this.remoteAddress = request.getRemoteAddr();

        HttpSession session = request.getSession(false);
        this.sessionId = (session != null) ? session.getId() : null;
        StringBuilder builder = new StringBuilder();
        if (remoteAddress != null) {
            builder.append("remoteAddress=").append(remoteAddress);
        }
        if (builder.length() > 1) {
            builder.append(", ");
        }
        if (sessionId != null) {
            builder.append("sessionId=<SESSION>");
            if (builder.length() > 1) {
                builder.append(", ");
            }
        }
        if (tokenType != null) {
            builder.append("tokenType=").append(this.tokenType);
        }
        if (tokenValue != null) {
            builder.append("tokenValue=<TOKEN>");
        }
        this.display = builder.toString();
    }

    @Override
    public String toString() {
        return display;
    }

}