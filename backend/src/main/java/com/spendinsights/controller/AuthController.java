package com.spendinsights.controller;

import com.spendinsights.dto.AuthDtos.*;
import com.spendinsights.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService svc;

    @PostMapping("/register")
    AuthResponse register(@Valid @RequestBody RegisterRequest r) {
        return svc.register(r);
    }

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest r) {
        return svc.login(r);
    }
}
