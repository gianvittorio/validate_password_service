package com.gianvittorio.validate_password.integration;

import com.gianvittorio.validate_password.constants.ValidadePasswordConstants;
import com.gianvittorio.validate_password.dto.RequestDTO;
import com.gianvittorio.validate_password.service.ValidatePasswordService;
import com.gianvittorio.validate_password.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.*;
import static com.gianvittorio.validate_password.utils.TestUtils.INVALID_DEFAULT_PASSWORD;
import static com.gianvittorio.validate_password.utils.TestUtils.VALID_DEFAULT_PASSWORD;

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
    @DisplayName("Must return true whenever provided password is valid")
    public void validPasswordTest() {
        RequestDTO requestDTO = RequestDTO.builder()
                .password(VALID_DEFAULT_PASSWORD)
                .build();

        webTestClient.post()
                .uri(uri.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.password").isEqualTo(VALID_DEFAULT_PASSWORD)
                .jsonPath("$.isValid").isEqualTo(true);
    }

    @Test
    @DisplayName("Must return false whenever provided password is valid")
    public void invalidPasswordTest() {
        RequestDTO requestDTO = RequestDTO.builder()
                .password(INVALID_DEFAULT_PASSWORD)
                .build();

        webTestClient.post()
                .uri(uri.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.password").isEqualTo(INVALID_DEFAULT_PASSWORD)
                .jsonPath("$.isValid").isEqualTo(false);
    }

    @Test
    @DisplayName("Must return 400 and specific null password error message within the body")
    public void throwWhenPasswordIsNullTest() {

        RequestDTO requestDTO = RequestDTO.builder()
                .password(null)
                .build();

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
    }

    @Test
    @DisplayName("Must return 400 and specific malformed password error message within the body")
    public void throwWhenPasswordIsMalformedTest() {
        RequestDTO requestDTO = RequestDTO.builder()
                .password("")
                .build();

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
    }

    @DisplayName("Run miscellaneous parameterized tests")
    @ParameterizedTest(name = "{index} => password={0}, expectation={1}")
    @MethodSource("argumentsProvider")
    public void miscellaneousTest(String password, boolean expectation) {
        RequestDTO requestDTO = RequestDTO.builder()
                .password(password)
                .build();

        webTestClient.post()
                .uri(uri.concat("/validate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), RequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.password").isEqualTo(password)
                .jsonPath("$.isValid").isEqualTo(expectation);
    }

    private static Stream<Arguments> argumentsProvider() {
        return TestUtils.argumentsProvider();
    }
}
