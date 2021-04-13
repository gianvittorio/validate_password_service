package com.gianvittorio.validate_password.lib;

public class DigitValidator extends SingleCharacterMatchingValidator {

    public DigitValidator() {
        super(Character::isDigit);
    }
}
