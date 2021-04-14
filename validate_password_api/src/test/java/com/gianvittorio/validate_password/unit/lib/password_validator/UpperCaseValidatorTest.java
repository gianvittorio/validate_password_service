package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.UpperCaseValidator;
import com.gianvittorio.validate_password.unit.lib.password_validator.util.BasePasswordValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpperCaseValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new UpperCaseValidator();
    }

    @Test
    @DisplayName("Must return true whenever at least one uppercase character is present")
    public void atLeastOneUpperCaseCharacterTest() {
        final String password = "A";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no uppercase character is present")
    public void upperCaseCharacterIsAbsentTest() {
        assertThat(passwordValidator.isValid("".toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}

