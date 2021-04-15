package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.lib.password_validator.SizeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SizeValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new SizeValidator(ValidadePasswordConstants.MINIMUM_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("Must return true whenever password length is equal to minimum")
    public void minimumLengthTest() {
        final String password = "012345678";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return true whenever password length is equal to minimum")
    public void aboveMinimumLengthTest() {
        final String password = "0123456789";

        assertThat(passwordValidator.isValid(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever password length is below minimum")
    public void belowMinimumLengthTest() {
        assertThat(passwordValidator.isValid("".toCharArray())).isFalse();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }
}
