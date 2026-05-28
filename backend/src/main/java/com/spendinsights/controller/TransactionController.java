package com.spendinsights.controller;

import com.spendinsights.dto.TransactionDtos.*;
import com.spendinsights.security.UserPrincipal;
import com.spendinsights.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService svc;

    @GetMapping
    List<TransactionResponse> list(@AuthenticationPrincipal UserPrincipal p) {
        return svc.list(p.user());
    }

    @GetMapping("/summary")
    DashboardSummary summary(@AuthenticationPrincipal UserPrincipal p) {
        return svc.dashboard(p.user());
    }

    @GetMapping("/ai-summary")
    Map<String, String> aiSummary(@AuthenticationPrincipal UserPrincipal p) {
        return svc.aiSummary(p.user());
    }
}