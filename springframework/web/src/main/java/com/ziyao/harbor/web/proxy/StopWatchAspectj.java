package com.ziyao.harbor.web.proxy;

import com.ziyao.harbor.core.metrics.StopWatches;
import com.ziyao.harbor.core.metrics.Watch;
import com.ziyao.harbor.core.utils.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author ziyao zhang
 * @since 2024/3/22
 */
@Aspect
public class StopWatchAspectj {

    private final StopWatchConfig config;

    public StopWatchAspectj(StopWatchConfig config) {
        this.config = config;
    }

    @Around(value = "@annotation(watch)")
    public Object around(ProceedingJoinPoint point, Watch watch) throws Throwable {

        if (!config.isEnabled()) {
            return point.proceed();
        }

        if (config.getUniqCodes().stream()
                .anyMatch(uniq -> uniq.equals(watch.value()))) {
            try {
                StopWatches.enabled(Strings.isEmpty(
                        watch.description()) ? watch.value() : watch.description());

                StopWatches.start("总耗时");
                return point.proceed();
            } finally {
                StopWatches.stop("总耗时");
                StopWatches.consolePrettyPrintOfLoggerOrOut();
                StopWatches.disabled();
            }
        } else return point.proceed();
    }

    public static void main(String[] args) {
        StopWatches.enabled("哈哈哈");
        StopWatches.start("测试");
        StopWatches.stop("测试");
        StopWatches.consolePrettyPrintOfLoggerOrOut();
        StopWatches.disabled();
    }
}
