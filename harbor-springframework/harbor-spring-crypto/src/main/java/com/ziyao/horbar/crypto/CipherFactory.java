package com.ziyao.horbar.crypto;

import com.ziyao.harbor.crypto.TextCipher;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface CipherFactory {

    List<TextCipher> create(Object properties);
}
