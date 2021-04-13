package com.gianvittorio.validate_password.unit.service;

import com.gianvittorio.validate_password.lib.PasswordValidator;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import com.gianvittorio.validate_password.service.ValidatePasswordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ValidatePasswordServiceTest {
    @Mock
    private PasswordValidator passwordValidator;

    @InjectMocks
    private ValidatePasswordService validatePasswordService = new ValidatePasswordServiceImpl();

    @Test
    @DisplayName("Must call password validator's isValid() underneath")
    public void callIsValidTest() {
        Mockito.when(passwordValidator.isValid(any()))
                .thenReturn(any(Boolean.class));

        validatePasswordService.isValid("");

        Mockito.verify(passwordValidator)
                .isValid(any());
    }
}
