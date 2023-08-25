package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.utils.Strings;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author ziyao zhang
 * @since 2023/5/17
 */
public final class SecurityPredicate implements Predicate<String> {

    private final Set<String> skipApis;

    private final Set<String> illegalApis;
    private final AntPathMatcher matcher = new AntPathMatcher();

    private SecurityPredicate(Set<String> skipApis, Set<String> illegalApis) {
        if (CollectionUtils.isEmpty(skipApis))
            this.skipApis = new HashSet<>();
        else
            this.skipApis = skipApis;

        if (CollectionUtils.isEmpty(illegalApis))
            this.illegalApis = new HashSet<>();
        else
            this.illegalApis = illegalApis;

    }

    public static SecurityPredicate initIllegalApis(Set<String> illegalApis) {
        return new SecurityPredicate(null, illegalApis);
    }

    public static SecurityPredicate initSecurityApis(Set<String> skipApis) {
        return new SecurityPredicate(skipApis, null);
    }


    public SecurityPredicate addIllegalApis(Set<String> illegalApis) {
        if (!CollectionUtils.isEmpty(illegalApis)) {
            this.illegalApis.addAll(illegalApis);
        }
        return this;
    }

    public SecurityPredicate addSecurityApis(Set<String> skipApis) {
        if (!CollectionUtils.isEmpty(skipApis)) {
            this.skipApis.addAll(skipApis);
        }
        return this;
    }

    @Override
    public boolean test(String callApi) {
        if (!CollectionUtils.isEmpty(skipApis)) {
            if (Strings.hasLength(callApi))
                return this.skipApis.stream().anyMatch(skipApi -> matcher.match(skipApi, callApi));
            else
                return false;
        }
        if (!CollectionUtils.isEmpty(illegalApis)) {
            if (Strings.hasLength(callApi))
                return this.illegalApis.stream().anyMatch(skipApi -> matcher.match(skipApi, callApi));
            else
                return false;
        }
        return false;
    }

    public boolean isIllegal(String callApi) {
        return this.test(callApi);
    }

    public boolean skip(String callApi) {
        return this.test(callApi);
    }
}
