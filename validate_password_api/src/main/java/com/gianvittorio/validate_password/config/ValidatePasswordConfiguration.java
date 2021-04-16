package com.gianvittorio.validate_password.config;

import com.gianvittorio.validate_password.lib.password_validator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.MINIMUM_PASSWORD_LENGTH;

@Configuration
public class ValidatePasswordConfiguration {

    @Bean
    public PasswordValidator passwordValidator() {
        return new AnyForbiddenCharacterValidator()
                .addValidator(SizeValidator.builder().minimumSize(MINIMUM_PASSWORD_LENGTH).build())
                .addValidator(new LowerCaseCharacterValidator())
                .addValidator(new UpperCaseCharacterValidator())
                .addValidator(new SpecialCharacterValidator())
                .addValidator(new RepeatedCharacterValidator());
    }
}
