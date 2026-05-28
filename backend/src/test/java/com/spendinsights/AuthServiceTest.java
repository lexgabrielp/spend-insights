package com.spendinsights;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceTest {
    @Test
    void bcryptMatches() {
        var e = new BCryptPasswordEncoder(12);
        String h = e.encode("password123");
        assertThat(e.matches("password123", h)).isTrue();
    }
}
