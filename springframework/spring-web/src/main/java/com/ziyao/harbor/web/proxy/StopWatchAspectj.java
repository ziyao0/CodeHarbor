package com.ziyao.harbor.web.proxy;

import com.ziyao.harbor.core.metrics.StopWatches;
import com.ziyao.harbor.core.metrics.Watches;
import com.ziyao.harbor.web.ResponseBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ziyao zhang
 * @since 2024/3/22
 */
@Aspect
public class StopWatchAspectj {

    private static final Logger log = LoggerFactory.getLogger(StopWatchAspectj.class);

    @Around(value = "@annotation(watches)")
    public Object around(ProceedingJoinPoint point, Watches watches) {
        try {
            StopWatches.enabled(watches.value());
            StopWatches.start("总耗时");
            return point.proceed();
        } catch (Throwable e) {
            log.error("stopwatch error {}", e.getMessage(), e);
            return ResponseBuilder.failed();
        } finally {
            StopWatches.stop("总耗时");
            StopWatches.consolePrettyPrint();
            StopWatches.disabled();
        }
    }
}
