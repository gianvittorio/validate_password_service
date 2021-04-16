package com.gianvittorio.validate_password.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsConfiguration implements WebFluxConfigurer {

    @Value("${server.port}")
    private int serverPort;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("")
                .allowedOrigins("http://localhost:" + serverPort)
                .allowedHeaders("Authorization")
                .allowedMethods("GET");
    }
}
