package com.spendinsights.security;

import com.spendinsights.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

@Service
public class JwtService {
    private final byte[] secret;
    private final long accessMinutes;
    private final long refreshDays;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.access-minutes}") long am, @Value("${app.jwt.refresh-days}") long rd) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.accessMinutes = am;
        this.refreshDays = rd;
    }

    public String access(User u) {
        return token(u, Duration.ofMinutes(accessMinutes), "access");
    }

    public String refresh(User u) {
        return token(u, Duration.ofDays(refreshDays), "refresh");
    }

    private String token(User u, Duration d, String typ) {
        Instant now = Instant.now();
        return Jwts.builder().subject(u.getEmail()).claim("uid", u.getId().toString()).claim("role", u.getRole().name()).claim("typ", typ).issuedAt(Date.from(now)).expiration(Date.from(now.plus(d))).signWith(Keys.hmacShaKeyFor(secret)).compact();
    }

    public Jws<Claims> parse(String t) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret)).build().parseSignedClaims(t);
    }
}
