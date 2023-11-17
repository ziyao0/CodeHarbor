package com.ziyao.harbor.crypto;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface ConstantPool {

    String key = "com.ziyao.crypto.cipher.sm4.key";
    String iv = "com.ziyao.crypto.cipher.sm4.iv";
    String input_path = "com.ziyao.crypto.config.location-in";
    String output_path = "com.ziyao.crypto.config.location-out";
    String included_keys = "com.ziyao.crypto.keys";
}
