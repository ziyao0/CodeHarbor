package com.ziyao.harbor.core;

import com.ziyao.harbor.core.utils.RegexPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;

/**
 * @author ziyao zhang
 * @since 2024/3/20
 */
public abstract class NetworkFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkFinder.class);

    public static String find(String source) {
        Matcher ipMatcher = RegexPool.IP4.forName().matcher(source);
        if (ipMatcher.find()) {
            return ipMatcher.group();
        }
        Matcher domainMatcher = RegexPool.DOMAIN.forName().matcher(source);
        if (domainMatcher.find()) {
            return domainMatcher.group();
        }
        return null;
    }

    private NetworkFinder() {
    }
}
