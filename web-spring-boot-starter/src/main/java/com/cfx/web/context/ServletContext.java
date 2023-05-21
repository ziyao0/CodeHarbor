package com.cfx.web.context;

import com.cfx.common.exception.UnauthorizedException;
import com.cfx.web.details.UserDetails;
import com.cfx.web.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public record ServletContext(HttpServletRequest request, HttpServletResponse response) implements ContextInfo {

    @Override
    public boolean isAuthentication() {
        return ContextUtils.isLegal();
    }

    @Override
    public void assertAuthentication() throws UnauthorizedException {
        if (!isAuthentication()) {
            throw new UnauthorizedException();
        }
    }

    @Override
    public UserDetails getUserDetails() {
        return null;
    }

    public static void main(String[] args) {
        ServletContext servletContext = new ServletContext(null, null);
    }
}
