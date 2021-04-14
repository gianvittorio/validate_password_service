package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.SpecialCharacterValidator;
import com.gianvittorio.validate_password.unit.lib.password_validator.util.BasePasswordValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialCharacterValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new SpecialCharacterValidator();
    }

    @Test
    @DisplayName("Must return true whenever at least one special character is present")
    public void atLeastOneSpecialCharacterTest() {
        final String password = "ab+";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no special character is present")
    public void specialCharacterIsAbsentTest() {
        final String password = "ab";

        assertThat(passwordValidator.isValid(password.toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}
