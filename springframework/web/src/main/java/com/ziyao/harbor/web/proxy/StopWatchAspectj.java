package com.ziyao.harbor.web.proxy;

import com.ziyao.harbor.core.metrics.StopWatches;
import com.ziyao.harbor.core.metrics.Watch;
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

    @Around(value = "@annotation(watch)")
    public Object around(ProceedingJoinPoint point, Watch watch) throws Throwable {
        try {
            StopWatches.enabled(watch.value());
            StopWatches.start("总耗时");
            return point.proceed();
        } finally {
            StopWatches.stop("总耗时");
            StopWatches.consolePrettyPrint();
            StopWatches.disabled();
        }
    }
}
