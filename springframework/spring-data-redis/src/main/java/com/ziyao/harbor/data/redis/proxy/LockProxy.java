package com.ziyao.harbor.data.redis.proxy;

import com.ziyao.harbor.data.redis.lock.Lock;
import com.ziyao.harbor.data.redis.lock.LockCallback;
import com.ziyao.harbor.data.redis.lock.RuleMode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ziyao zhang
 * @since 2024/3/20
 */
@Aspect
public class LockProxy {

    private static final Logger logger = LoggerFactory.getLogger(LockProxy.class);
    private final RedissonClient redissonClient;

    public LockProxy(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around(value = "@annotation(lock)")
    public Object around(ProceedingJoinPoint point, Lock lock) throws Throwable {
        RLock rLock = redissonClient.getLock(lock.value());

        RuleMode rule = lock.rule();
        if (rLock.isLocked()) {
            switch (rule) {
                case SKIP: {
                    return invoke(lock.callback());
                }
                case AWAIT: {
                    return tryLock(rLock, lock, point);
                    // 执行锁等待逻辑
                }
                default: {
                    return tryLock(rLock, lock, point);
                }
            }
        } else {
            return tryLock(rLock, lock, point);
        }
    }


    private Object tryLock(RLock rLock, Lock lock, ProceedingJoinPoint point) throws Throwable {
        if (rLock.tryLock(lock.waitTime(), lock.leaseTime(), lock.Unit())) {
            try {
                return point.proceed();
            } catch (Exception e) {
                logger.error("执行任务异常：{}", e.getMessage());
                throw e;
            } finally {
                rLock.unlock();
            }
        } else {
            return invoke(lock.callback());
        }
    }

    private Object invoke(Class<? extends LockCallback> callbackClass) {
        try {
            Method method = callbackClass.getMethod(LockCallback.METHOD_NAME);
            LockCallback callbackInstance = callbackClass.getDeclaredConstructor().newInstance();
            return method.invoke(callbackInstance);
        } catch (NoSuchMethodException | InvocationTargetException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
