package com.gianvittorio.validate_password.lib.password_validator;

public interface PasswordValidator {

    boolean isValidImpl(char[] password);

    default boolean isValid(char[] password) {
        if (password == null || password.length == 0) {
            return false;
        }

        return isValidImpl(password);
    }
}
