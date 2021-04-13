package com.gianvittorio.validate_password.web.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1)
@RequiredArgsConstructor
public class ValidatePasswordController{

    private final ValidatePasswordService validatePasswordService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Mono<Boolean> isValid(@RequestHeader("Authorization") String authorizationHeader) {
        String authorization = authorizationHeader.replace("Basic ", "");

        return validatePasswordService.isValid(authorization);
    }
}
