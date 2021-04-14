package com.gianvittorio.validate_password.lib.password_validator;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Builder
public class SingleCharacterMatchingValidator extends PasswordValidatorChain {

    private final Predicate<? super Character> characterMatcher;

    @Override
    public boolean isValidImpl(char[] password) {
        boolean result = IntStream.range(0, password.length)
                .mapToObj(pointer -> password[pointer])
                .anyMatch(characterMatcher::test);

        if (!result) {
            return false;
        }

        return super.isValidImpl(password);
    }
}
