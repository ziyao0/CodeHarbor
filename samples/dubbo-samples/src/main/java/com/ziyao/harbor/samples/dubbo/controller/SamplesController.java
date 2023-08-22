package com.ziyao.harbor.samples.dubbo.controller;

import com.ziyao.harbor.samples.dubbo.service.SamplesService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziyao zhang
 * @since 2023/5/11
 */
@RestController
public class SamplesController {


    @DubboReference
    private SamplesService samplesService;

    @GetMapping("/dubbo/hello")
    public String hello() {
        return samplesService.hello();
    }
}
