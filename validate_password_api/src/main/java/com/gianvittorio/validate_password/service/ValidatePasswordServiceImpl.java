package com.gianvittorio.validate_password.service;

import com.gianvittorio.validate_password.lib.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ValidatePasswordServiceImpl implements ValidatePasswordService {

    @Autowired
    private PasswordValidator passwordValidator;

    @Override
    public Mono<Boolean> isValid(String password) {
        return Mono.just(passwordValidator.isValid(password.toCharArray()));
    }
}
