package com.gianvittorio.validate_password.lib.password_validator;

public class DigitCharacterValidator extends AnyMatchingCharacterValidator {

    public DigitCharacterValidator() {
        super(Character::isDigit);
    }
}
