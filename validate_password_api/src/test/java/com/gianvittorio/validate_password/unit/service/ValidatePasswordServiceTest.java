package com.gianvittorio.validate_password.unit.service;

import com.gianvittorio.validate_password.lib.password_validator.PasswordValidator;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import com.gianvittorio.validate_password.service.impl.ValidatePasswordServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ValidatePasswordServiceTest {
    @MockBean
    private PasswordValidator passwordValidator;

    private ValidatePasswordService validatePasswordService;

    @Test
    @DisplayName("Must call password validator's isValid() underneath")
    public void callIsValidTest() {
        validatePasswordService = new ValidatePasswordServiceImpl(passwordValidator);

        Mockito.when(passwordValidator.isValid(any()))
                .thenReturn(false);

        validatePasswordService.validate("password")
                .block();

        Mockito.verify(passwordValidator)
                .isValid(any());
    }
}
