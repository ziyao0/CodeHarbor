package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.Validator;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authenticate.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.authenticate.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedExceptions;

/**
 * @author ziyao zhang
 * @since 2023/12/12
 */
public abstract class PasswordValidator implements Validator<PasswordParameter> {

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();

    private static final PasswordValidator passwordValidator = new PasswordValidator() {
        @Override
        public void validate(PasswordParameter parameter) {
            super.validate(parameter);
        }
    };

    @Override
    public void validate(PasswordParameter parameter) {

        Assert.notNull(parameter, AuthenticatedExceptions.createValidatedFailure());
        Assert.notNull(parameter.plaintext(), AuthenticatedExceptions.createValidatedFailure());
        Assert.notNull(parameter.ciphertext(), AuthenticatedExceptions.createValidatedFailure());


        doValidated(parameter);


    }

    private void doValidated(PasswordParameter parameter) {
        if (!passwordEncryptor.matches(parameter.plaintext(), parameter.ciphertext())) {
            throw AuthenticatedExceptions.createValidatedFailure();
        }
    }

    public static void validated(PasswordParameter parameter) {
        passwordValidator.validate(parameter);
    }
}
