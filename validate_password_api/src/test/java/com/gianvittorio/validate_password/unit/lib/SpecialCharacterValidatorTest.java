package com.gianvittorio.validate_password.unit.lib;

import com.gianvittorio.validate_password.lib.SpecialCharacterValidator;
import com.gianvittorio.validate_password.unit.lib.util.BasePasswordValidatorTest;
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

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no special character is present")
    public void specialCharacterIsAbsentTest() {
        final String password = "ab";

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isFalse();
    }
}
