package com.gianvittorio.validate_password.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${security_scheme}")
    private String securityScheme;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new
                Info().title(securityScheme).version("1.0.0").description(securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(securityScheme))
                .components(new Components().addSecuritySchemes(securityScheme,
                        new SecurityScheme().name("security").type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}
