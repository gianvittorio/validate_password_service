package com.gianvittorio.validate_password.unit.lib;

import com.gianvittorio.validate_password.lib.RepeatedCharacterValidator;
import com.gianvittorio.validate_password.unit.lib.util.BasePasswordValidatorTest;
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

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isTrue();
    }

    @Test
    @DisplayName("Must return false whenever at least one character is repeated")
    public void specialCharacterIsAbsentTest() {
        final String password = "aba";

        assertThat(passwordValidator.isValidImpl(password.toCharArray())).isFalse();
    }
}
