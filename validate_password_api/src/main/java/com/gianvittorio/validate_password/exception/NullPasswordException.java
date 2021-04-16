package com.gianvittorio.validate_password.exception;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.NULL_PASSWORD_DEFAULT_ERROR_MESSAGE;

public class NullPasswordException extends RuntimeException {

    public NullPasswordException() {
        super(NULL_PASSWORD_DEFAULT_ERROR_MESSAGE);
    }
}
