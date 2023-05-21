package com.cfx.web.context;

/**
 * 通过线程绑定 {@link ContextInfo} 对象的形式开放web请求信息
 *
 * @author zhangziyao
 * @date 2023/4/23
 */
public class ContextManager {

    private static final ThreadLocal<ContextInfo> contextManager = new ThreadLocal<>();

    /**
     * 返回绑定到当前线程的 {@link ContextInfo}.
     *
     * @return 绑定到当前线程的 {@link ContextInfo}, 如果未绑定则返回 {@code null}
     */
    public static ContextInfo get() {
        return contextManager.get();
    }

    /**
     * 绑定 {@link ContextInfo} 到当前线程.
     *
     * @param contextInfo 公开的 {@link ContextInfo}, 为 {@code null} 时将重置线程绑定的
     *                    {@link ContextInfo}
     * @see #reset()
     */
    public static void set(ContextInfo contextInfo) {
        if (contextInfo == null) {
            reset();
        } else {
            contextManager.set(contextInfo);
        }
    }

    /**
     * 返回绑定到当前线程的 {@link ContextInfo}, 当未绑定或请求未认证时会抛出异常.
     *
     * @return 绑定到当前线程的 {@link ContextInfo}
     */
    public static ContextInfo getValidContextInfo() {
        ContextInfo contextInfo = ContextManager.get();
        contextInfo.assertAuthentication();
        return contextInfo;
    }

    /**
     * 重置 ContextInfo
     */
    public static void reset() {
        contextManager.remove();
    }
}
