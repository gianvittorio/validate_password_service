package com.gianvittorio.validate_password.exception;

import lombok.Getter;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.MALFORMED_PASSWORD_DEFAULT_ERROR_MESSAGE;

@Getter
public class MalFormedPasswordException extends RuntimeException {
    private String password;

    public MalFormedPasswordException(String password) {
        super(MALFORMED_PASSWORD_DEFAULT_ERROR_MESSAGE);
        this.password = password;
    }
}
