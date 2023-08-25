package com.ziyao.harbor.web.context;

import com.ziyao.harbor.core.token.Tokens;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.web.details.UserDetails;
import com.ziyao.harbor.web.exception.UnauthorizedException;
import com.ziyao.harbor.web.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public final class ServletContext implements ContextInfo {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserDetails userDetails;

    /**
     * 构建 servlet context
     */
    public ServletContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.userDetails = UserDetailsConstructor.build(request);
    }

    @Override
    public boolean isAuthentication() {
        return ContextUtils.isLegal(this.userDetails);
    }

    @Override
    public void assertAuthentication() throws UnauthorizedException {
        if (!isAuthentication()) {
            throw new UnauthorizedException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServletContext) obj;
        return Objects.equals(this.request, that.request) &&
                Objects.equals(this.response, that.response);
    }


    @Override
    public String toString() {
        return "ServletContext{" +
                "request=" + request +
                ", response=" + response +
                ", userDetails=" + userDetails +
                '}';
    }

    @Override
    public HttpServletRequest request() {
        return request;
    }

    @Override
    public HttpServletResponse response() {
        return response;
    }

    @Override
    public UserDetails getUser() {
        return userDetails;
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response, userDetails);
    }


    final static class UserDetailsConstructor {

        private static UserDetails build(HttpServletRequest request) {
            String appid = getValue(request, Tokens.TokenConverter.APP_ID);
            String userId = getValue(request, Tokens.TokenConverter.USER_ID);
            String username = getValue(request, Tokens.TokenConverter.USERNAME);
            String nickname = getValue(request, Tokens.TokenConverter.NICKNAME);
            String phone = getValue(request, Tokens.TokenConverter.PHONE);
            String email = getValue(request, Tokens.TokenConverter.EMAIL);
            String deptId = getValue(request, Tokens.TokenConverter.DEPT_ID);
            String deptName = getValue(request, Tokens.TokenConverter.DEPT_NAME);

            return UserDetails.builder()
                    .appid(Strings.hasLength(appid) ? Long.parseLong(appid) : null)
                    .userId(Strings.hasLength(userId) ? Long.parseLong(userId) : null)
                    .username(username)
                    .nickname(nickname)
                    .phone(phone)
                    .email(email)
                    .deptId(Strings.hasLength(deptId) ? Long.parseLong(deptId) : null)
                    .deptName(deptName)
                    .additionalInformation(getAdditionalInformation(request))
                    .build();
        }

        private static String getValue(HttpServletRequest request, String key) {
            String value = request.getHeader(key);
            if (Strings.hasLength(value)) {
                return URLDecoder.decode(value, StandardCharsets.UTF_8);
            }
            return value;
        }

        private static Map<String, Object> getAdditionalInformation(HttpServletRequest request) {
            String ip = request.getHeader(Tokens.IP);

            HashMap<String, Object> additionalInformation = new HashMap<>();
            additionalInformation.put(Tokens.IP, ip);
            return additionalInformation;
        }
    }
}
