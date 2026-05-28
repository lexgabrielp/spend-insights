package com.spendinsights.ai;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class OllamaClient {
    private final WebClient web;
    private final String chatModel;
    private final String embedModel;

    public OllamaClient(@Value("${app.ollama.base-url}") String url, @Value("${app.ollama.chat-model}") String cm, @Value("${app.ollama.embed-model}") String em) {
        web = WebClient.builder().baseUrl(url).build();
        chatModel = cm;
        embedModel = em;
    }

    public String chat(String prompt) {
        Map res = web.post().uri("/api/generate").bodyValue(Map.of("model", chatModel, "prompt", prompt, "stream", false)).retrieve().bodyToMono(Map.class).block();
        return Objects.toString(res.get("response"), "");
    }

    public java.util.List<Double> embed(String text) {
        Map res = web.post().uri("/api/embeddings").bodyValue(Map.of("model", embedModel, "prompt", text)).retrieve().bodyToMono(Map.class).block();
        return (List<Double>) res.getOrDefault("embedding", List.of());
    }
}
