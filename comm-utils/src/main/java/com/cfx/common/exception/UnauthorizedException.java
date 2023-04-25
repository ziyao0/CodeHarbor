package com.cfx.common.exception;

import com.cfx.common.writer.Errors;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public class UnauthorizedException extends ServiceException {
    private static final long serialVersionUID = 1350454124169036151L;

    public UnauthorizedException() {
        super(Errors.UNAUTHORIZED);
    }
}
