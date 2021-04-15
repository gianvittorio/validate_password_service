package com.gianvittorio.validate_password.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.dto.RequestDTO;
import com.gianvittorio.validate_password.dto.ResponseDTO;
import com.gianvittorio.validate_password.exception.MalFormedPasswordException;
import com.gianvittorio.validate_password.exception.NullPasswordException;
import com.gianvittorio.validate_password.lib.codec.Base64Codec;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.Map;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;

@RestController
@RequestMapping(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1)
@RequiredArgsConstructor
@Slf4j
public class ValidatePasswordController {

    private final ValidatePasswordService validatePasswordService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            value = "/validate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Password validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password is valid"),
            @ApiResponse(responseCode = "400", description = "Password is either null or malformed",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = {@ExampleObject(value = NULL_PASSWORD_DEFAULT_ERROR_MESSAGE), @ExampleObject(value = MALFORMED_PASSWORD_DEFAULT_ERROR_MESSAGE)})
            )
    })
    public Mono<ResponseDTO> validatePassword(@Valid @RequestBody RequestDTO requestDTO) throws MalformedURLException {
        String password = requestDTO.getPassword();
        if (password == null) {
            throw new NullPasswordException();
        }

        if (password.isBlank()) {
            throw new MalFormedPasswordException(requestDTO.getPassword());
        }

        return Mono.just(requestDTO)
                .flatMap(req ->
                        validatePasswordService.isValid(password)
                                .map(isValid -> ResponseDTO.builder().password(password).isValid(isValid).build())
                );
    }
}
