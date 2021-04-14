package com.gianvittorio.validate_password.service.impl;

import com.gianvittorio.validate_password.lib.password_validator.PasswordValidator;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ValidatePasswordServiceImpl implements ValidatePasswordService {

    private final PasswordValidator passwordValidator;

    @Override
    public Mono<Boolean> isValid(String password) {
        return Mono.just(password.toCharArray())
                .map(passwordValidator::isValid)
                .log();
    }
}
