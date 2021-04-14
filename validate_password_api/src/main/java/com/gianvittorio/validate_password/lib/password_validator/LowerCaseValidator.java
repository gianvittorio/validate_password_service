package com.gianvittorio.validate_password.lib.password_validator;

public class LowerCaseValidator extends SingleCharacterMatchingValidator {

    public LowerCaseValidator() {
        super(Character::isLowerCase);
    }
}
