package com.cfx.samples.dubbo.service.impl;

import com.cfx.samples.dubbo.service.SamplesService;
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
