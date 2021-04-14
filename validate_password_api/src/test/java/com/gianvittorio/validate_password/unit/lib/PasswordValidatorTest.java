package com.gianvittorio.validate_password.unit.lib;

import com.gianvittorio.validate_password.config.ValidatePasswordConfiguration;
import com.gianvittorio.validate_password.unit.lib.util.BasePasswordValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidatorTest extends BasePasswordValidatorTest {

    @BeforeEach
    public void setUp() {
        passwordValidator = new ValidatePasswordConfiguration()
                .passwordValidator();
    }

    @Test
    @DisplayName("Must return false whenever password is null")
    public void nullPasswordTest() {
        assertThat(passwordValidator.isValid(null)).isFalse();
    }

    @Test
    @DisplayName("Run miscellaneous parameterized tests")
    public void miscellaneousTest() {
        Map<String, Boolean> parameters = Map.of(
                "", false,
                "aa", false,
                "ab", false,
                "AAAbbbCc", false,
                "AbTp9!foo", false,
                "AbTp9!foA", false,
                "AbTp9 fok", false,
                "AbTp9!fok", true);

        parameters.keySet()
                .stream()
                .forEach(password -> {
                    boolean expectation = parameters.get(password);

                    assertThat(passwordValidator.isValid(password.toCharArray())).isEqualTo(expectation);
                });
    }
}
