package com.gianvittorio.validate_password.unit.controller;

import com.gianvittorio.validate_password.controller.ValidatePasswordController;
import com.gianvittorio.validate_password.lib.codec.Base64Codec;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
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

    private Base64Codec base64Codec = new Base64Codec();

    @Test
    @DisplayName("Must validate password whenever is provided")
    public void validatePasswordTest() {
        String encodedCredentials = base64Codec.encode(DEFAULT_USER.concat(":").concat(INVALID_DEFAULT_PASSWORD));

        given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(true));

        webTestClient.get()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE_PREFIX.concat(encodedCredentials))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class);

        verify(validatePasswordService)
                .isValid(any(String.class));
    }

    @Test
    @DisplayName("Must throw whenever password is absent")
    public void throwWhenPasswordIsAbsent() {
        webTestClient.get()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .consumeWith(consumer -> assertThat(consumer.getResponseBody()).isEqualTo(DEFAULT_ERROR_MESSAGE));

        verify(validatePasswordService, never())
                .isValid(any(String.class));
    }

    @Test
    @DisplayName("Must validate password whenever is provided")
    public void throwWhenPasswordIsMalformedTest() {
        given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(true));

        webTestClient.get()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1.concat("/validate"))
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE_PREFIX.concat(DEFAULT_USER))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .consumeWith(consumer -> assertThat(consumer.getResponseBody()).isEqualTo(DEFAULT_ERROR_MESSAGE));

        verify(validatePasswordService, never())
                .isValid(any(String.class));
    }
}
