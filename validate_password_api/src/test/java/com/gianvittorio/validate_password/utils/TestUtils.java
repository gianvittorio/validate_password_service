package com.gianvittorio.validate_password.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public interface TestUtils {
    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("aa", false),
                Arguments.of("ab", false),
                Arguments.of("AAAbbbCc", false),
                Arguments.of("AbTp9!foo", false),
                Arguments.of("AbTp9!foA", false),
                Arguments.of("AbTp9 fok", false),
                Arguments.of("AbTp9!fok", true),
                Arguments.of("123456abC!", true),
                Arguments.of("123456abC!", true),
                Arguments.of("123456abC! ", false)
        );
    }
}
