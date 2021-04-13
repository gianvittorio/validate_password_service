package com.gianvittorio.validate_password.web.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ValidatePasswordControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.port}")
    private int port;

    @Test
    @DisplayName("Can map url")
    public void helloWorldTest() {
        final String uri = "http://localhost:"
                .concat(Integer.toString(port))
                .concat(ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1);

        final String expectedBody = "Hello World";

        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(consumer -> {
                    assertThat(consumer.getResponseBody()).isEqualTo(expectedBody);
                });
    }
}
