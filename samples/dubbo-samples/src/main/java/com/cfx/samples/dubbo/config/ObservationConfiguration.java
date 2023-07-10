//package com.cfx.samples.dubbo.config;
//
//import io.micrometer.observation.ObservationHandler;
//import io.micrometer.observation.ObservationRegistry;
//import org.apache.dubbo.rpc.model.ApplicationModel;
//import org.apache.skywalking.apm.toolkit.micrometer.observation.SkywalkingDefaultTracingHandler;
//import org.apache.skywalking.apm.toolkit.micrometer.observation.SkywalkingReceiverTracingHandler;
//import org.apache.skywalking.apm.toolkit.micrometer.observation.SkywalkingSenderTracingHandler;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author zhangziyao
// * @since 2023/4/23
// */
//@Configuration
//public class ObservationConfiguration {
//
//
//    @Bean
//    @ConditionalOnBean(ObservationRegistry.class)
//    public ApplicationModel applicationModel(ObservationRegistry observationRegistry) {
//        ApplicationModel applicationModel = ApplicationModel.defaultModel();
//        observationRegistry.observationConfig()
//                .observationHandler(new ObservationHandler.FirstMatchingCompositeObservationHandler(
//                        new SkywalkingSenderTracingHandler(),
//                        new SkywalkingReceiverTracingHandler(),
//                        new SkywalkingDefaultTracingHandler()
//                ));
//        applicationModel.getBeanFactory().registerBean(observationRegistry);
//        return applicationModel;
//    }
//}
