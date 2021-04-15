package com.gianvittorio.validate_password.lib.password_validator;

public class SpecialCharacterValidator extends AnyMatchingCharacterValidator {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-+";

    public SpecialCharacterValidator() {
        super(character -> SPECIAL_CHARACTERS.contains(String.valueOf(character)));
    }
}
