package com.ziyao.harbor.gateway.route;

import com.ziyao.harbor.core.lang.NonNull;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author ziyao zhang
 * @since 2023/9/7
 */
@Component
public class RouteEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 发布
     */
    public void publish() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
