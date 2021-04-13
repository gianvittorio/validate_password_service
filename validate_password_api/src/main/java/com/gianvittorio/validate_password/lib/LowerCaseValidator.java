package com.gianvittorio.validate_password.lib;

public class LowerCaseValidator extends SingleCharacterMatchingValidator {

    public LowerCaseValidator() {
        super(Character::isLowerCase);
    }
}
