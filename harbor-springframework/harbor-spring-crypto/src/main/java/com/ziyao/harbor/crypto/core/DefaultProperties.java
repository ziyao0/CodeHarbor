package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.Properties;
import com.ziyao.harbor.crypto.utils.ConstantPool;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public class DefaultProperties extends Properties<DefaultProperties> {
    @Override
    public String getPrefix() {
        return ConstantPool.default_prefix;
    }
}
