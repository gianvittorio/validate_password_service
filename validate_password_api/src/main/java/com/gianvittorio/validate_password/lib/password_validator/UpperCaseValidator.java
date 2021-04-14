package com.gianvittorio.validate_password.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.SingleCharacterMatchingValidator;

public class UpperCaseValidator extends SingleCharacterMatchingValidator {

    public UpperCaseValidator() {
        super(Character::isUpperCase);
    }
}
