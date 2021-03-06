package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.LowerCaseCharacterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LowerCaseCharacterValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new LowerCaseCharacterValidator();
    }

    @Test
    @DisplayName("Must return true whenever at least one lowercase character is present")
    public void atLeastOneLowerCaseCharacterTest() {
        final String password = "a";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no lowercase character is present")
    public void lowerCaseCharacterIsAbsentTest() {
        assertThat(passwordValidator.isValid("".toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}
