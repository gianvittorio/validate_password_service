package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.lib.password_validator.RepeatedCharacterValidator;
import com.gianvittorio.validate_password.unit.lib.password_validator.util.BasePasswordValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RepeatedCharacterValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new RepeatedCharacterValidator();
    }

    @Test
    @DisplayName("Must return true whenever there are no repeated character")
    public void atLeastOneSpecialCharacterTest() {
        final String password = "abc";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever at least one character is repeated")
    public void specialCharacterIsAbsentTest() {
        final String password = "aba";

        assertThat(passwordValidator.isValid(password.toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}
