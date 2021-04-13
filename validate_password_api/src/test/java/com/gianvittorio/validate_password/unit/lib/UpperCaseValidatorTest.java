package com.gianvittorio.validate_password.unit.lib;

import com.gianvittorio.validate_password.lib.UpperCaseValidator;
import com.gianvittorio.validate_password.unit.lib.util.BasePasswordValidatorTest;
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

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no uppercase character is present")
    public void upperCaseCharacterIsAbsentTest() {
        assertThat(passwordValidator.isValidImpl("".toCharArray())).isFalse();
    }
}
