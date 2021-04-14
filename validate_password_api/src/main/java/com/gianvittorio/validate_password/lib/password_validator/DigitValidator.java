package com.gianvittorio.validate_password.lib.password_validator;

public class DigitValidator extends SingleCharacterMatchingValidator {

    public DigitValidator() {
        super(Character::isDigit);
    }
}
