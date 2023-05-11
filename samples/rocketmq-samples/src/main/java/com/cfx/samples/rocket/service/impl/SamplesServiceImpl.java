package com.cfx.samples.rocket.service.impl;

import com.cfx.samples.rocket.service.SamplesService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Eason
 * @since 2023/5/11
 */
@DubboService
public class SamplesServiceImpl implements SamplesService {
    @Override
    public String hello() {
        return "hello";
    }
}
