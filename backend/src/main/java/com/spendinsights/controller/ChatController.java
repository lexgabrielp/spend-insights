package com.spendinsights.controller;

import com.spendinsights.dto.ChatDtos.*;
import com.spendinsights.security.UserPrincipal;
import com.spendinsights.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService svc;

    @PostMapping
    ChatResponse ask(@AuthenticationPrincipal UserPrincipal p, @Valid @RequestBody ChatRequest r) {
        return svc.ask(p.user(), r);
    }
}
