package com.ziyao.harbor.crypto.exception;



/**
 * @author ziyao
 * @since 2023/4/23
 */
public class CryptoException extends RuntimeException {
    
    private static final long serialVersionUID = 7228570590120864863L;

    public CryptoException(String message) {
        super(message);
    }


    public CryptoException(Throwable e) {
        super(e);
    }

}
