package com.gianvittorio.validate_password.lib.password_validator;

public class LowerCaseCharacterValidator extends AnyMatchingCharacterValidator {

    public LowerCaseCharacterValidator() {
        super(Character::isLowerCase);
    }
}
