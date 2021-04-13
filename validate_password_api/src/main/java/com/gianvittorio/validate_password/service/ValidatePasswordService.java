package com.gianvittorio.validate_password.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface ValidatePasswordService {

    Mono<Boolean> isValid(String password);
}
