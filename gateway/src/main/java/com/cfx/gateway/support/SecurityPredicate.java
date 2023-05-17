package com.cfx.gateway.support;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author ziyao zhang
 * @since 2023/5/17
 */
public class SecurityPredicate implements Predicate<String> {

    private Set<String> skipApis;
    private final AntPathMatcher matcher = new AntPathMatcher();

    protected SecurityPredicate(Set<String> skipApis) {
        if (CollectionUtils.isEmpty(skipApis))
            this.skipApis = new HashSet<>();
        else
            this.skipApis = skipApis;
    }

    public static SecurityPredicate initSecurityApis(Set<String> skipApis) {
        return new SecurityPredicate(skipApis);
    }


    public SecurityPredicate add(Set<String> skipApis) {
        if (!CollectionUtils.isEmpty(skipApis)) {
            this.skipApis.addAll(skipApis);
        }
        return this;
    }

    @Override
    public boolean test(String callApi) {
        if (StringUtils.hasLength(callApi))
            return this.skipApis.stream().anyMatch(skipApi -> matcher.match(skipApi, callApi));
        else
            return false;
    }

    public boolean skip(String callApi) {
        return this.test(callApi);
    }

    public void setSkipApis(Set<String> skipApis) {
        this.skipApis = skipApis;
    }
}
