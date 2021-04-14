package com.gianvittorio.validate_password.constants;

public class ValidadePasswordConstants {
    public static final String VALIDATE_PASSWORD_ENDPOINT_V1 = "/api/v1";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Basic ";

    public static final String DEFAULT_ERROR_MESSAGE = AUTHORIZATION_HEADER + " header is either absent or malformed.";

    public static final long MINIMUM_PASSWORD_LENGTH = 9;

    public static final String DEFAULT_USER = "spring";

    public static final String INVALID_DEFAULT_PASSWORD = "webflux";

    public static final String VALID_DEFAULT_PASSWORD = "AbTp9!fok";
}
