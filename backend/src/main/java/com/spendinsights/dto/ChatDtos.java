package com.spendinsights.dto;

import jakarta.validation.constraints.*;

import java.util.*;

public class ChatDtos {
    public record ChatRequest(UUID conversationId, @NotBlank String message) {
    }

    public record ChatResponse(UUID conversationId, String answer, java.util.List<String> citations) {
    }
}
