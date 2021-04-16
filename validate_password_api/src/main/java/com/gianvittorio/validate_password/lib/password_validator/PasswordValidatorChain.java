package com.gianvittorio.validate_password.lib.password_validator;

public abstract class PasswordValidatorChain implements PasswordValidator {

    private PasswordValidatorChain next = null;

    @Override
    public boolean isValidImpl(char[] password) {
        if (next == null) {
            return true;
        }

        return next.isValidImpl(password);
    }

    public PasswordValidatorChain addValidator(PasswordValidatorChain validator) {
        if (next == null) {
            next = validator;

            return this;
        }

        next = next.addValidator(validator);

        return this;
    }
}
