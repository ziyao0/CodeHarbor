package com.ziyao.harbor.core.error;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public abstract class Errors {


    /**
     * 通用: 请求成功.
     */
    public static final StatusMessage SUCCESS = StatusMessage.getInstance(StatusMessage.SUCCESS_STATE(), "请求成功");
    /**
     * 通用: 参数错误.
     */
    public static final StatusMessage ILLEGAL_ARGUMENT = StatusMessage.getInstance(400, "参数错误");

    /**
     * 通用请求未认证.
     */
    public static final StatusMessage UNAUTHORIZED = StatusMessage.getInstance(401, "请求未认证");

    /**
     * 登录失败
     */
    public static final StatusMessage LOGIN_FAILED = StatusMessage.getInstance(4011, "登录失败");

    /**
     * 禁止访问
     */
    public static final StatusMessage FORBIDDEN = StatusMessage.getInstance(403, "禁止访问");

    /**
     * 资源不存在
     */
    public static final StatusMessage NOT_FOUND_ERROR = StatusMessage.getInstance(404, "资源不存在");
    /**
     * 请求超时
     */
    public static final StatusMessage REQUEST_TIMEOUT_ERROR = StatusMessage.getInstance(408, "请求超时");

    /**
     * 通用: 服务器内部错误.
     */
    public static final StatusMessage INTERNAL_SERVER_ERROR = StatusMessage.getInstance(500, "服务器内部错误");

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

    private Errors() {
    }
}