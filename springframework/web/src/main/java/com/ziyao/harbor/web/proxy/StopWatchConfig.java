package com.ziyao.harbor.web.proxy;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/05/15 09:46:03
 */
@Data
public class StopWatchConfig {

    /**
     * 总开关
     */
    private boolean enabled;
    /**
     * 任务列表
     */
    private Set<String> uniqCodes = new HashSet<>();
}
