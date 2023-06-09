package com.ziyao.cfx.gateway.common;

import com.ziyao.cfx.gateway.security.api.Provider;
import com.ziyao.cfx.gateway.security.api.ProviderManager;
import com.ziyao.cfx.gateway.security.core.AuthorizationProviderManager;
import com.ziyao.cfx.gateway.support.ApplicationContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Configuration
public class GatewayAutoConfiguration implements ApplicationContextAware {


    /**
     * 初始化 ReactiveStringRedisTemplate
     *
     * @param connectionFactory redis连接工厂
     * @return {@link ReactiveRedisConnectionFactory}
     */
    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveStringRedisTemplate(connectionFactory);
    }


    /**
     * 初始化 ReactiveRedisTemplate
     *
     * @param connectionFactory redis连接工厂
     * @return {@link ReactiveRedisConnectionFactory}
     */
    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, redisSerializationContext());
    }

    /**
     * redis reactive serialization
     * <p>
     * 默认json序列化
     * <code>
     * final GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
     * </code>
     *
     * @return {@link RedisSerializationContext}
     * @see org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
     */
    public RedisSerializationContext<String, Object> redisSerializationContext() {
        // 对象序列化器
        final RedisSerializationContext.SerializationPair<Object> objectSerializationPair
                = RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());
        // 字符串序列化器
        final RedisSerializationContext.SerializationPair<String> stringSerializationPair
                = RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext();

        builder.hashKey(stringSerializationPair);
        builder.hashValue(objectSerializationPair);
        builder.key(stringSerializationPair);
        builder.value(objectSerializationPair);
        builder.string(stringSerializationPair);
        return builder.build();
    }

    @Bean
    public ProviderManager providerManager() {
        List<Provider> providers = ApplicationContextUtils.getBeansOfType(Provider.class);
        return new AuthorizationProviderManager(providers);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
////        return builder.routes()
////                .route("example_route", r -> r.path("/example")
//////                        .filters(f -> f.filter(customGatewayFilter1()).filter(customGatewayFilter2()))
////                        /*.uri("http://example.com")*/)
////                .build();
//
//        return builder.routes().route("route", r -> r.path("/**").filters(
//                f -> f.filter(ApplicationContextUtils.getBean(GatewayPreFilter.class))
//                        .filter(ApplicationContextUtils.getBean(AuthCenterFilter.class))
//                        .filter(ApplicationContextUtils.getBean(GatewayPostFilter.class))
//        ).uri("http://127.0.0.1:8888")).build();
//    }
}
