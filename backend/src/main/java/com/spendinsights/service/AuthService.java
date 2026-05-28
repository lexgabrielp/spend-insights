package com.spendinsights.service;

import com.spendinsights.domain.*;
import com.spendinsights.dto.AuthDtos.*;
import com.spendinsights.repository.UserRepository;
import com.spendinsights.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final AuthenticationManager auth;

    public AuthResponse register(RegisterRequest r) {
        if (users.findByEmail(r.email()).isPresent()) throw new IllegalArgumentException("Email already registered");
        User u = new User();
        u.setName(r.name());
        u.setEmail(r.email().toLowerCase());
        u.setPasswordHash(encoder.encode(r.password()));
        users.save(u);
        return new AuthResponse(jwt.access(u), jwt.refresh(u), "Bearer");
    }

    public AuthResponse login(LoginRequest r) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.password()));
        User u = users.findByEmail(r.email()).orElseThrow();
        return new AuthResponse(jwt.access(u), jwt.refresh(u), "Bearer");
    }
}
