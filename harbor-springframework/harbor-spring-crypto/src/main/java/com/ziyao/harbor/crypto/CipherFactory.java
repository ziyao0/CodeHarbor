package com.ziyao.harbor.crypto;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface CipherFactory {

    List<TextCipher> create(CipherProperties properties);
}
