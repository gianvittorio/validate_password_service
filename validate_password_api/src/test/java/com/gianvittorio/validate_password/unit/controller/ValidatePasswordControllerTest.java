package com.gianvittorio.validate_password.unit.controller;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import com.gianvittorio.validate_password.web.controller.ValidatePasswordController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.VALIDATE_PASSWORD_ENDPOINT_V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {ValidatePasswordController.class})
@ActiveProfiles("test")
public class ValidatePasswordControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ValidatePasswordService validatePasswordService;

    @Test
    @DisplayName("")
    public void simpleTest() {
        BDDMockito.given(validatePasswordService.isValid(any(String.class)))
                .willReturn(Mono.just(false));

        webTestClient.get()
                .uri(VALIDATE_PASSWORD_ENDPOINT_V1)
                .header("Authorization", "Basic blahblah")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .consumeWith(consumer -> assertThat(consumer.getResponseBody()).isFalse());

        BDDMockito.verify(validatePasswordService)
                .isValid(any(String.class));
    }
}
