package com.gianvittorio.validate_password.lib.password_validator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class AnyForbiddenCharacterValidator extends PasswordValidatorChain {

    protected Set<Character> forbidden = new HashSet<>();

    public AnyForbiddenCharacterValidator() {
        addForbidden(' ');
    }

    @Override
    public boolean isValidImpl(char[] password) {
        boolean result = IntStream.range(0, password.length)
                .mapToObj(pointer -> String.valueOf(password[pointer]))
                .anyMatch(character -> forbidden.toString().contains(character));

        if (result) {
            return false;
        }

        return super.isValidImpl(password);
    }

    public final void addForbidden(Character character) {
        forbidden.add(character);
    }
}
