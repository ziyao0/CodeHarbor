package com.cfx.openapi.service.impl;

import com.cfx.openapi.service.OpenService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@DubboService(group = "open-api", version = "1.0.0")
public class OpenServiceImpl implements OpenService {
    @Override
    public void open() {

    }
}
