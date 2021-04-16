package com.gianvittorio.validate_password.service;

import com.gianvittorio.validate_password.model.entity.Result;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface ValidatePasswordService {

    Mono<Result> validate(String password);
}
