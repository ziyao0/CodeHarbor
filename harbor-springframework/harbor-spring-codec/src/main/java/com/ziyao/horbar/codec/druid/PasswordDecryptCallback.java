package com.ziyao.horbar.codec.druid;

import com.alibaba.druid.util.DruidPasswordCallback;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2023/10/18
 */
public class PasswordDecryptCallback extends DruidPasswordCallback {
    @Serial
    private static final long serialVersionUID = -969291393141855980L;


    @Override
    public char[] getPassword() {
        return super.getPassword();
    }
}