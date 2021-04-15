package com.gianvittorio.validate_password.unit.controller;

import com.gianvittorio.validate_password.controller.ValidatePasswordController;
import com.gianvittorio.validate_password.dto.RequestDTO;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;
import static com.gianvittorio.validate_password.utils.TestUtils.VALID_DEFAULT_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {ValidatePasswordController.class})
@ActiveProfiles("test")
public class ValidatePasswordControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ValidatePasswordService validatePasswordService;

    @Test
    @DisplayName("Must return 200 and validate password whenever is provided")
    public void validatePasswordTest() {
        RequestDTO requestDTO = RequestDTO.builder()
                .password(VALID_DEFAULT_PASSWORD)
                .build();

        given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(true));

        webTestClient.post()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.password").isEqualTo(VALID_DEFAULT_PASSWORD)
                .jsonPath(".isValid").isEqualTo(true);

        verify(validatePasswordService)
                .isValid(any(String.class));
    }

    @Test
    @DisplayName("Must return 400 and specific null password error message within the body")
    public void throwWhenPasswordIsNullTest() {

        RequestDTO requestDTO = RequestDTO.builder()
                .password(null)
                .build();

        given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(true));

        webTestClient.post()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.password").isEmpty()
                .jsonPath(".errorMessage").isEqualTo(NULL_PASSWORD_DEFAULT_ERROR_MESSAGE);

        verify(validatePasswordService, never())
                .isValid(any(String.class));
    }

    @Test
    @DisplayName("Must return 400 and specific malformed password error message within the body")
    public void throwWhenPasswordIsMalformedTest() {
        RequestDTO requestDTO = RequestDTO.builder()
                .password("")
                .build();

        given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(true));

        webTestClient.post()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.password").isEqualTo("")
                .jsonPath(".errorMessage").isEqualTo(MALFORMED_PASSWORD_DEFAULT_ERROR_MESSAGE);

        verify(validatePasswordService, never())
                .isValid(any(String.class));
    }
}
