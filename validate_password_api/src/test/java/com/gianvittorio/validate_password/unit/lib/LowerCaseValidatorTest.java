package com.gianvittorio.validate_password.unit.lib;

import com.gianvittorio.validate_password.lib.LowerCaseValidator;
import com.gianvittorio.validate_password.unit.lib.util.BasePasswordValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LowerCaseValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new LowerCaseValidator();
    }

    @Test
    @DisplayName("Must return true whenever at least one lowercase character is present")
    public void atLeastOneLowerCaseCharacterTest() {
        final String password = "a";

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever no lowercase character is present")
    public void lowerCaseCharacterIsAbsentTest() {
        assertThat(passwordValidator.isValidImpl("".toCharArray())).isFalse();
    }
}
