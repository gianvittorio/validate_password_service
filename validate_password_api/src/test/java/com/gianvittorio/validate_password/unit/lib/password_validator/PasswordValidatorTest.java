package com.gianvittorio.validate_password.unit.lib.password_validator;

import com.gianvittorio.validate_password.config.ValidatePasswordConfiguration;
import com.gianvittorio.validate_password.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @DisplayName("Run miscellaneous parameterized tests")
    @ParameterizedTest(name = "{index} => password={0}, expectation={1}")
    @MethodSource("argumentsProvider")
    public void miscellaneousTest(String password, boolean expectation) {
        assertThat(passwordValidator.isValid(password.toCharArray()))
                .isEqualTo(expectation);
    }

    private static Stream<Arguments> argumentsProvider() {
        return TestUtils.argumentsProvider();
    }
}
