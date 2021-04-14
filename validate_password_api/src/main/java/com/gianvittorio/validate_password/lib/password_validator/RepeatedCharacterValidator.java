package com.gianvittorio.validate_password.lib.password_validator;

import java.util.HashSet;
import java.util.Set;

public class RepeatedCharacterValidator extends PasswordValidatorChain {

    @Override
    public boolean isValidImpl(char[] password) {
        if(hasRepeatedCharacters(password)) {
            return false;
        }

        return super.isValidImpl(password);
    }

    private static boolean hasRepeatedCharacters(char[] password) {
        final Set<Character> visited = new HashSet<>();

        for (char character : password) {
            if (visited.contains(character)) {
                return true;
            }

            visited.add(character);
        }
        return false;
    }
}
