package com.gianvittorio.validate_password.lib;

public class SpecialCharacterValidator extends SingleCharacterMatchingValidator {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-+";

    public SpecialCharacterValidator() {
        super(character -> SPECIAL_CHARACTERS.contains(String.valueOf(character)));
    }
}
