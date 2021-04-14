package com.gianvittorio.validate_password.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.lib.codec.Base64Codec;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;

@RestController
@RequestMapping(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1)
@RequiredArgsConstructor
@Slf4j
public class ValidatePasswordController {

    private final ValidatePasswordService validatePasswordService;

    private final Base64Codec base64Codec = new Base64Codec();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/validate")
    @Operation(summary = "Password validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password is valid"),
            @ApiResponse(responseCode = "400", description = "Password is either absent or malformed",
                    content = @Content(schema = @Schema(implementation = String.class), examples = {@ExampleObject(value = DEFAULT_ERROR_MESSAGE)}))
    })
    public Mono<Boolean> isValid(@RequestHeader Map<String, String> headers) {
        String base64credentials = headers.get(AUTHORIZATION_HEADER);
        if (base64credentials == null || !base64credentials.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            log.error(DEFAULT_ERROR_MESSAGE);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DEFAULT_ERROR_MESSAGE);
        }

        log.info("Incoming password validation request: {}", base64credentials);

        String credentials = base64Codec.decode(base64credentials);
        String[] userWithPassword = credentials.split(":", 2);
        if (userWithPassword == null || userWithPassword.length < 2) {
            log.error(DEFAULT_ERROR_MESSAGE);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DEFAULT_ERROR_MESSAGE);
        }

        String password = userWithPassword[1];

        return Mono.just(password)
                .flatMap(pwd -> validatePasswordService.isValid(pwd))
                .log();
    }
}
