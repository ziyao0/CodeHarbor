package com.ziyao.harbor.web;

import com.google.common.collect.Lists;
import com.ziyao.harbor.web.context.ContextManager;
import com.ziyao.harbor.web.context.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/5/22
 */
@Slf4j
@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter extends OncePerRequestFilter {

    private final List<String> excludePaths;

    private final AntPathMatcher matcher = new AntPathMatcher();

    public RequestFilter() {
        this.excludePaths = Lists.newArrayList(
                "/actuator/**",
                "/error/**",
                "/auth/**",
                "/oauth2/**"
        );
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        ServletContext servletContext = new ServletContext(request, response);
        ContextManager.set(servletContext);
        if (!servletContext.isAuthentication()) {
            log.debug("请求未认证，URL:{}", request.getRequestURL());
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return this.excludePaths.stream()
                .anyMatch(skipApi -> matcher.match(skipApi, request.getRequestURI()));
    }
}
