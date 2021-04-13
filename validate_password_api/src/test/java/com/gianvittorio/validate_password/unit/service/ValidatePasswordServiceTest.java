package com.gianvittorio.validate_password.unit.service;

import com.gianvittorio.validate_password.service.ValidatePasswordService;
import com.gianvittorio.validate_password.service.ValidatePasswordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ValidatePasswordServiceTest {

    private ValidatePasswordService validatePasswordService;

    @BeforeEach
    public void setUp() {
        validatePasswordService = new ValidatePasswordServiceImpl();
    }

    @Test
    @DisplayName("Must return false")
    public void simpleTest() {
        String s = "blahblah";

        Mono<Boolean> result = validatePasswordService.isValid(s);

        StepVerifier.create(result.log())
                .expectSubscription()
                .expectNext(false)
                .expectComplete();
    }
}
