package com.ziyao.harbor.data.redis.support;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/3/1
 */
abstract public class TimeoutUtils {

    /**
     * Check if a given Duration can be represented in {@code sec} or requires {@code msec} representation.
     *
     * @param duration the actual {@link Duration} to inspect. Never {@literal null}.
     * @return {@literal true} if the {@link Duration} contains millisecond information.
     * @since 2.1
     */
    public static boolean hasMillis(Duration duration) {
        return duration.toMillis() % 1000 != 0;
    }

    /**
     * Converts the given timeout to seconds.
     * <p>
     * Since a 0 timeout blocks some Redis ops indefinitely, this method will return 1 if the original value is greater
     * than 0 but is truncated to 0 on conversion.
     *
     * @param timeout The timeout to convert
     * @param unit    The timeout's unit
     * @return The converted timeout
     */
    public static long toSeconds(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toSeconds(timeout));

    }

    /**
     * Converts the given timeout to milliseconds.
     * <p>
     * Since a 0 timeout blocks some Redis ops indefinitely, this method will return 1 if the original value is greater
     * than 0 but is truncated to 0 on conversion.
     *
     * @param timeout The timeout to convert
     * @param unit    The timeout's unit
     * @return The converted timeout
     */
    public static long toMillis(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout));
    }

    private static long roundUpIfNecessary(long timeout, long convertedTimeout) {
        // A 0 timeout blocks some Redis ops indefinitely, round up if that's
        // not the intention
        if (timeout > 0 && convertedTimeout == 0) {
            return 1;
        }
        return convertedTimeout;
    }

    public static void main(String[] args) {
        System.out.println(toSeconds(-1, TimeUnit.DAYS));
    }
}
