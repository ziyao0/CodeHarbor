package com.ziyao.harbor.core.error;

import com.ziyao.harbor.core.error.exception.HarborException;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
public abstract class Exceptions {

    public Exceptions() {
    }


    /**
     * 通用: 请求成功.
     */
    public static final StatusMessage SUCCESS = StatusMessage.getInstance(StatusMessage.SUCCESS_STATE(), "请求成功");

    /**
     * 创建非法访问
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createIllegalArgumentException(String message) {
        return new HarborException(400, "参数错误：" + message);
    }

    /**
     * 登录失败
     */
    public static HarborException createLoginFailedException(String message) {
        return new HarborException(4011, "登录失败：" + message);
    }

    /**
     * 资源不存在
     */
    public static HarborException createNotFoundException(String message) {
        return new HarborException(404, "资源不存在：" + message);
    }

    /**
     * 请求超时
     */

    public static HarborException createRequestTimeoutException(String message) {
        return new HarborException(408, "请求超时：" + message);
    }

    /**
     * 通用: 服务器内部错误.
     */
    public static HarborException createInternalServerErrorException(String message) {
        return new HarborException(500, "服务器内部错误：" + message);
    }

    /**
     * 通用: 用户未登录.
     */
    public static final StatusMessage USER_NOT_LOGIN = StatusMessage.getInstance(530, "用户未登录");

    /**
     * 通用: 新增失败.
     */
    public static final StatusMessage INSERT_FAIL = StatusMessage.getInstance(10000, "新增失败");

    /**
     * 通用: 更新失败.
     */
    public static final StatusMessage UPDATE_FAIL = StatusMessage.getInstance(10001, "更新失败");

    /**
     * 通用: 删除失败.
     */
    public static final StatusMessage DELETE_FAIL = StatusMessage.getInstance(10002, "删除失败");

    /**
     * 通用: 批量操作执行失败.
     */
    public static final StatusMessage BATCH_ERROR = StatusMessage.getInstance(10003, "批量操作执行失败");
    /**
     * 通用: 解析token算法不匹配.
     */
    public static final StatusMessage ALGORITHM_MISMATCH = StatusMessage.getInstance(401, "解析Token算法不匹配");
    /**
     * 通用: 校验签名失败.
     */
    public static final StatusMessage SIGNATURE_VERIFICATION = StatusMessage.getInstance(401, "校验签名异常");
    /**
     * 通用: token过期.
     */
    public static final StatusMessage TOKEN_EXPIRED = StatusMessage.getInstance(401, "Token过期");
    /**
     * 通用: token非法.
     */
    public static final StatusMessage JWT_DECODE = StatusMessage.getInstance(401, "Token非法");
    /**
     * 通用: 操作token异常.
     */
    public static final StatusMessage TOKEN_ERROR = StatusMessage.getInstance(401, "解析Token异常");


    /**
     * 创建请求未认证异常
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createUnauthorizedException(String message) {
        return new HarborException(401, message);
    }

    /**
     * 创建越权访问异常
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createForbiddenException(String message) {
        return new HarborException(403, message);
    }

    /**
     * 创建非法访问
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createIllegalAccessException(String message) {
        return new HarborException(403, message);
    }
}
