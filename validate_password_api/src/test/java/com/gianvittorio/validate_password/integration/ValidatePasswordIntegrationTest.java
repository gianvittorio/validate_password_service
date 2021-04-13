package com.gianvittorio.validate_password.integration;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ValidatePasswordIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ValidatePasswordService validatePasswordService;

    @LocalServerPort
    private int port;

    private String uri;

    @BeforeEach
    public void beforeAll() {
        uri = "http://localhost:"
                .concat(Integer.toString(port))
                .concat(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1);
    }

    @Test
    @DisplayName("Must validate password, once it is available as a header")
    public void validatePasswordTest() {
        webTestClient.get()
                .uri(uri.concat("/validate"))
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE_PREFIX.concat("blah blah"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .consumeWith(consumer -> {
                    assertThat(consumer.getResponseBody()).isFalse();
                });
    }

    @Test
    @DisplayName("Must return 400 whenever Authorization header is absent")
    public void mustThrowIfAuthorizationIsAbsentTest() {
        webTestClient.get()
                .uri(uri.concat("/validate"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .consumeWith(consumer -> assertThat(consumer.getResponseBody()).isEqualTo(DEFAULT_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Must return 400 whenever Authorization header is malformed")
    public void mustThrowIfAuthorizationIsMalformedTest() {
        webTestClient.get()
                .uri(uri.concat("/validate"))
                .header(AUTHORIZATION_HEADER, "blah blah")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .consumeWith(consumer -> assertThat(consumer.getResponseBody()).isEqualTo(DEFAULT_ERROR_MESSAGE));
    }
}
