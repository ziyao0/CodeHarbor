package com.ziyao.harbor.core.error;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public abstract class Errors {


    public static final int E_500 = 500;
    public static final int E_404 = 404;
    public static final int E_403 = 403;
    public static final int E_401 = 401;
    public static final int E_400 = 400;
    public static final int E_200 = 200;

    /**
     * 通用: 请求成功.
     */
    public static final IMessage SUCCESS = IMessage.getInstance(IMessage.SUCCESS_STATE(), "请求成功");
    /**
     * 通用: 参数错误.
     */
    public static final IMessage ILLEGAL_ARGUMENT = IMessage.getInstance(E_400, "参数错误");

    /**
     * 通用请求未认证.
     */
    public static final IMessage UNAUTHORIZED = IMessage.getInstance(E_401, "请求未认证");

    /**
     * 登录失败
     */
    public static final IMessage LOGIN_FAILED = IMessage.getInstance(4011, "登录失败");

    /**
     * 禁止访问
     */
    public static final IMessage FORBIDDEN = IMessage.getInstance(E_403, "禁止访问");

    /**
     * 资源不存在
     */
    public static final IMessage NOT_FOUND_ERROR = IMessage.getInstance(E_404, "资源不存在");
    /**
     * 请求超时
     */
    public static final IMessage REQUEST_TIMEOUT_ERROR = IMessage.getInstance(408, "请求超时");

    /**
     * 通用: 服务器内部错误.
     */
    public static final IMessage INTERNAL_SERVER_ERROR = IMessage.getInstance(500, "服务器内部错误");

    /**
     * 通用: 用户未登录.
     */
    public static final IMessage USER_NOT_LOGIN = IMessage.getInstance(530, "用户未登录");

    /**
     * 通用: 新增失败.
     */
    public static final IMessage INSERT_FAIL = IMessage.getInstance(10000, "新增失败");

    /**
     * 通用: 更新失败.
     */
    public static final IMessage UPDATE_FAIL = IMessage.getInstance(10001, "更新失败");

    /**
     * 通用: 删除失败.
     */
    public static final IMessage DELETE_FAIL = IMessage.getInstance(10002, "删除失败");

    /**
     * 通用: 批量操作执行失败.
     */
    public static final IMessage BATCH_ERROR = IMessage.getInstance(10003, "批量操作执行失败");
    /**
     * 通用: 解析token算法不匹配.
     */
    public static final IMessage ALGORITHM_MISMATCH = IMessage.getInstance(401, "解析Token算法不匹配");
    /**
     * 通用: 校验签名失败.
     */
    public static final IMessage SIGNATURE_VERIFICATION = IMessage.getInstance(401, "校验签名异常");
    /**
     * 通用: token过期.
     */
    public static final IMessage TOKEN_EXPIRED = IMessage.getInstance(401, "Token过期");
    /**
     * 通用: token非法.
     */
    public static final IMessage JWT_DECODE = IMessage.getInstance(401, "Token非法");
    /**
     * 通用: 操作token异常.
     */
    public static final IMessage TOKEN_ERROR = IMessage.getInstance(401, "解析Token异常");

    private Errors() {
    }
}