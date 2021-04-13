package com.gianvittorio.validate_password.lib;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class SizeValidator extends PasswordValidatorChain {

    private final long minimumSize;

    @Override
    public boolean isValidImpl(char[] password) {
        if (Long.compare(password.length, minimumSize) < 0) {
            return false;
        }

        return super.isValidImpl(password);
    }
}
