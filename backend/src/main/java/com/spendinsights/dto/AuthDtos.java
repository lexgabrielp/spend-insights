package com.spendinsights.dto;

import jakarta.validation.constraints.*;

public class AuthDtos {
    public record RegisterRequest(@NotBlank String name, @Email @NotBlank String email,
                                  @Size(min = 8) String password) {
    }

    public record LoginRequest(@Email String email, @NotBlank String password) {
    }

    public record AuthResponse(String accessToken, String refreshToken, String tokenType) {
    }

    public record RefreshRequest(@NotBlank String refreshToken) {
    }
}
