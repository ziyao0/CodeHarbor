package com.cfx.common.writer;

import com.cfx.common.api.MessageDetails;

/**
 * @author Eason
 * @date 2023/4/21
 */
public abstract class Errors {

    /**
     * 通用: 请求成功.
     */
    public static final MessageDetails SUCCESS = MessageDetails.getInstance(MessageDetails.SUCCESS_STATE(), "请求成功");

    /**
     * 通用: 参数错误.
     */
    public static final MessageDetails ILLEGAL_ARGUMENT = MessageDetails.getInstance(400, "参数错误");

    /**
     * 通用请求未认证.
     */
    public static final MessageDetails UNAUTHORIZED = MessageDetails.getInstance(401, "请求未认证");

    /**
     * 登录失败
     */
    public static final MessageDetails LOGIN_FAILED = MessageDetails.getInstance(4011, "登录失败");

    /**
     * 禁止访问
     */
    public static final MessageDetails FORBIDDEN = MessageDetails.getInstance(403, "禁止访问");

    /**
     * 资源不存在
     */
    public static final MessageDetails NOT_FOUND_ERROR = MessageDetails.getInstance(404, "资源不存在");
    /**
     * 请求超时
     */
    public static final MessageDetails REQUEST_TIMEOUT_ERROR = MessageDetails.getInstance(408, "请求超时");

    /**
     * 通用: 服务器内部错误.
     */
    public static final MessageDetails INTERNAL_SERVER_ERROR = MessageDetails.getInstance(500, "服务器内部错误");

    /**
     * 通用: 用户未登录.
     */
    public static final MessageDetails USER_NOT_LOGIN = MessageDetails.getInstance(530, "用户未登录");

    /**
     * 通用: 新增失败.
     */
    public static final MessageDetails INSERT_FAIL = MessageDetails.getInstance(10000, "新增失败");

    /**
     * 通用: 更新失败.
     */
    public static final MessageDetails UPDATE_FAIL = MessageDetails.getInstance(10001, "更新失败");

    /**
     * 通用: 删除失败.
     */
    public static final MessageDetails DELETE_FAIL = MessageDetails.getInstance(10002, "删除失败");

    /**
     * 通用: 批量操作执行失败.
     */
    public static final MessageDetails BATCH_ERROR = MessageDetails.getInstance(10003, "批量操作执行失败");


    private Errors() {
    }
}