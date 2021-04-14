package com.gianvittorio.validate_password.lib.codec;

import com.gianvittorio.validate_password.lib.codec.Codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Codec implements Codec<String, String> {
    @Override
    public String encode(String credentials) {
        if (credentials == null || credentials.isBlank()) {
            return "";
        }

        byte[] encodedCredentials = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));

        return new String(encodedCredentials, StandardCharsets.UTF_8);
    }

    @Override
    public String decode(String base64Credentials) {
        if (base64Credentials == null || base64Credentials.isBlank() || !base64Credentials.startsWith("Basic ")) {
            return "";
        }

        base64Credentials = base64Credentials.replace("Basic ", "");
        byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);

        return new String(decodedCredentials, StandardCharsets.UTF_8);
    }
}
