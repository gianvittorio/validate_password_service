package com.gianvittorio.validate_password.lib.password_validator;

public class UpperCaseCharacterValidator extends AnyMatchingCharacterValidator {

    public UpperCaseCharacterValidator() {
        super(Character::isUpperCase);
    }
}
