package com.gianvittorio.validate_password.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ValidatePasswordController {

    private final ValidatePasswordService validatePasswordService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/validate")
    @Operation(summary = "Check password is valid.")
    @Parameter(
            in = ParameterIn.HEADER,
            description = "Basic Authentication Password",
            name = "Authorization",
            example = "Basic AbTp9!fok",
            schema = @Schema(type = "string", defaultValue = "Basic AbTp9!fok", implementation = String.class)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password is valid"),
            @ApiResponse(responseCode = "400", description = "Password was not found amongst headers or it was malformed")
    })
    public Mono<Boolean> isValid(@RequestHeader Map<String, String> headers) {
        String authorization = headers.get(AUTHORIZATION_HEADER);
        if (authorization == null || !authorization.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            log.error(DEFAULT_ERROR_MESSAGE);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DEFAULT_ERROR_MESSAGE);
        }

        log.info("Incoming password validation request: {}", authorization);

        return Mono.just(authorization)
                .map(password -> password.replace(AUTHORIZATION_HEADER_VALUE_PREFIX, ""))
                .flatMap(password -> validatePasswordService.isValid(password))
                .log();
    }
}
