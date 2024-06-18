package com.ziyao.harbor.usercenter.authentication.support;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authentication.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.authentication.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.common.exception.AuthenticationExceptions;

/**
 * @author ziyao zhang
 * @since 2023/12/12
 */
public abstract class PasswordValidator {

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();

    private static final PasswordValidator passwordValidator = new PasswordValidator() {
        @Override
        public void validate(PasswordParameter parameter) {
            super.validate(parameter);
        }
    };

    public void validate(PasswordParameter parameter) {

        Assert.notNull(parameter, AuthenticationExceptions.createValidatedFailure());
        Assert.notNull(parameter.plaintext(), AuthenticationExceptions.createValidatedFailure());
        Assert.notNull(parameter.ciphertext(), AuthenticationExceptions.createValidatedFailure());
        doValidated(parameter);


    }

    private void doValidated(PasswordParameter parameter) {
        if (!passwordEncryptor.matches(parameter.plaintext(), parameter.ciphertext())) {
            throw AuthenticationExceptions.createValidatedFailure();
        }
    }

    public static void validated(PasswordParameter parameter) {
        passwordValidator.validate(parameter);
    }
}
