package com.gianvittorio.validate_password.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ValidatePasswordServiceImpl implements ValidatePasswordService{
    @Override
    public Mono<Boolean> isValid(String password) {
        return Mono.just(false);
    }
}
