package com.gianvittorio.validate_password.integration;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
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

    @Test
    @DisplayName("Must return false")
    public void helloWorldTest() {
        final String uri = "http://localhost:"
                .concat(Integer.toString(port))
                .concat(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1);

        webTestClient.get()
                .uri(uri)
                .header("Authorization", "Basic blahblah")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .consumeWith(consumer -> {
                    assertThat(consumer.getResponseBody()).isFalse();
                });
    }
}
