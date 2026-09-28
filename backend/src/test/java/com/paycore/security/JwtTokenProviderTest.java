package com.paycore.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Field;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {
    private JwtTokenProvider provider;

    @BeforeEach
    void setUp() throws Exception {
        provider = new JwtTokenProvider();
        byte[] secretBytes = new byte[32];
        for (int i = 0; i < secretBytes.length; i++) secretBytes[i] = (byte) (i + 1);

        Field secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(provider, Base64.getEncoder().encodeToString(secretBytes));

        Field expirationField = JwtTokenProvider.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(provider, 60_000);
    }

    @Test
    void testGenerateToken_AndExtractUsername() {
        User principal = User.withUsername("john.doe@paycore.com")
                .password("encoded").roles("EMPLOYEE").build();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        String token = provider.generateToken(authentication);
        assertNotNull(token);
        assertEquals("john.doe@paycore.com", provider.getUsernameFromJwtToken(token));
        assertTrue(provider.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_InvalidToken_ReturnsFalse() {
        assertFalse(provider.validateJwtToken("not-a-valid-jwt"));
    }

    @Test
    void testValidateJwtToken_TamperedToken_ReturnsFalse() {
        User principal = User.withUsername("john.doe@paycore.com")
                .password("encoded").roles("EMPLOYEE").build();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        String token = provider.generateToken(authentication);
        String tampered = token.substring(0, token.length() - 1) + "x";
        assertFalse(provider.validateJwtToken(tampered));
    }
}
