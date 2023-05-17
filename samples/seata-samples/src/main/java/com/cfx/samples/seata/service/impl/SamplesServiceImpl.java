package com.cfx.samples.seata.service.impl;

import com.cfx.samples.seata.service.SamplesService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author ziyao zhang
 * @since 2023/5/11
 */
@DubboService
public class SamplesServiceImpl implements SamplesService {
    @Override
    public String hello() {
        return "hello";
    }
}
