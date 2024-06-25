package com.ziyao.harbor.usercenter.service.app;

import com.ziyao.harbor.core.lang.Nullable;
import com.ziyao.security.oauth2.core.RegisteredApp;

/**
 * @author ziyao
 * @since 2024/06/08 17:26:35
 */
public interface RegisteredAppService {

    /**
     * 保存注册应用
     *
     * <p>
     * IMPORTANT: Sensitive information should be encoded externally from the
     * implementation, e.g. {@link RegisteredApp#getAppSecret()}
     *
     * @param registeredApp the {@link RegisteredApp}
     */
    void save(RegisteredApp registeredApp);

    /**
     * 通过应用id获取应用信息
     */
    @Nullable
    RegisteredApp findById(Long appId);

    /**
     * @return {@link Model}
     */
    default Model model() {
        return null;
    }

    enum Model {
        caffeine, redis, jpa

    }
}
