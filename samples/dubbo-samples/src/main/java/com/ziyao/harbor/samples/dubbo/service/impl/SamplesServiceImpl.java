package com.ziyao.harbor.samples.dubbo.service.impl;

import com.ziyao.harbor.samples.dubbo.service.SamplesService;
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
