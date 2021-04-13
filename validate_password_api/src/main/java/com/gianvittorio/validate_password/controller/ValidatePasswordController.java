package com.gianvittorio.validate_password.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;

@RestController
@RequestMapping(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1)
@RequiredArgsConstructor
public class ValidatePasswordController {

    private final ValidatePasswordService validatePasswordService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/validate")
    public Mono<Boolean> isValid(@RequestHeader Map<String, String> headers) {
        String authorization = headers.get(AUTHORIZATION_HEADER);
        if (authorization == null || !authorization.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DEFAULT_ERROR_MESSAGE);
        }

        return Mono.just(authorization)
                .map(password -> password.replace(AUTHORIZATION_HEADER_VALUE_PREFIX, ""))
                .flatMap(password -> validatePasswordService.isValid(password))
                .log();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getReason());
    }
}
