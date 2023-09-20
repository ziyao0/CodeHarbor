package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.usercenter.security.api.Authentication;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 认证管理器
 *
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface ProviderManager {


    /**
     * 认证处理 映射
     *
     * @param authentication {@link Authentication}认证核心参数
     * @return 返回认证结果
     */
    Authentication authenticate(Authentication authentication);

    /**
     * 初始化BeanName
     *
     * @param providers 认证bean
     * @return name to beanName
     */
    default Map<String, PrimaryProvider> initBeanMapping(List<? extends PrimaryProvider> providers) {
        return providers.stream().collect(
                Collectors.toMap(PrimaryProvider::getProviderName, Function.identity())
        );
    }
}
