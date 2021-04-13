package com.gianvittorio.validate_password.lib;

public class UpperCaseValidator extends SingleCharacterMatchingValidator {

    public UpperCaseValidator() {
        super(Character::isUpperCase);
    }
}
