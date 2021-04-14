package com.gianvittorio.validate_password.unit.lib.codec;

import com.gianvittorio.validate_password.lib.codec.Base64Codec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static com.gianvittorio.validate_password.constants.ValidadePasswordConstants.VALID_DEFAULT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

public class Base64CodecTest {

    private Base64Codec base64Codec = new Base64Codec();

    @Test
    @DisplayName("Must return non empty string on encoding non blank password")
    public void encodeTest() {
        assertThat(base64Codec.encode(VALID_DEFAULT_PASSWORD))
                .isNotBlank();
    }

    @Test
    @DisplayName("Must return empty string on encoding blank password")
    public void blankEncodeTest() {
        assertThat(base64Codec.encode(""))
                .isBlank();
    }

    @Test
    @DisplayName("Must return empty string on encoding blank password")
    public void nullEncodeTest() {
        assertThat(base64Codec.encode(null))
                .isBlank();
    }

    @Test
    @DisplayName("Encode password, prepend prefix, then decode. Must return original password")
    public void decodeTest() {
        String encodedCredentials = AUTHORIZATION_HEADER_VALUE_PREFIX
                .concat(base64Codec.encode(VALID_DEFAULT_PASSWORD));

        assertThat(base64Codec.decode(encodedCredentials))
                .isEqualTo(VALID_DEFAULT_PASSWORD);
    }

    @Test
    @DisplayName("Return empty string whenever password does not start with default prefix")
    public void absentPrefixDecodeTest() {
        assertThat(base64Codec.decode(base64Codec.encode(VALID_DEFAULT_PASSWORD)))
                .isBlank();
    }

    @Test
    @DisplayName("Return empty string whenever password is blank")
    public void blankPasswordDecodeTest() {
        assertThat(base64Codec.decode(base64Codec.encode(AUTHORIZATION_HEADER_VALUE_PREFIX)))
                .isBlank();
    }

    @Test
    @DisplayName("Return empty string whenever password is null")
    public void nullDecodeTest() {
        assertThat(base64Codec.decode(base64Codec.encode(null)))
                .isBlank();
    }
}
