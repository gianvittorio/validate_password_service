package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.AnyForbiddenCharacterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ForbiddenCharacterValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new AnyForbiddenCharacterValidator();
    }

    @Test
    @DisplayName("Must return false whenever at least one forbidden character is present")
    public void atLeastOneSpecialCharacterTest() {
        final String password = " a";

        assertThat(passwordValidator.isValid(password.toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return true when no  forbidden character is present")
    public void specialCharacterIsAbsentTest() {
        final String password = "ab";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}
