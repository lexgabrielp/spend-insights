package com.spendinsights.service;

import com.spendinsights.ai.OllamaClient;
import com.spendinsights.domain.*;
import com.spendinsights.dto.ChatDtos.*;
import com.spendinsights.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final AiConversationRepository convs;
    private final AiMessageRepository msgs;
    private final TransactionRepository txs;
    private final OllamaClient ai;

    @Transactional
    public ChatResponse ask(User u, ChatRequest req) {
        AiConversation c = req.conversationId() == null ? new AiConversation() : convs.findById(req.conversationId()).orElseThrow();
        if (c.getId() == null) {
            c.setUser(u);
            c.setTitle(req.message().substring(0, Math.min(50, req.message().length())));
            convs.save(c);
        }
        AiMessage m = new AiMessage();
        m.setConversation(c);
        m.setRole("user");
        m.setContent(req.message());
        msgs.save(m);
        var context = txs.findTop20ByUserIdOrderByPostedAtDesc(u.getId()).stream().map(t -> t.getPostedAt() + " | " + t.getMerchant() + " | " + t.getAmount() + " | " + (t.getCategory() == null ? "Uncategorized" : t.getCategory().getName())).toList();
        String prompt = "You are Spend Insights, a local-only financial analyst. Never claim access to external data. Answer from the user's transaction context. Context:\n" + context + "\nQuestion: " + req.message();
        String ans = ai.chat(prompt);
        AiMessage am = new AiMessage();
        am.setConversation(c);
        am.setRole("assistant");
        am.setContent(ans);
        msgs.save(am);
        return new ChatResponse(c.getId(), ans, context);
    }
}
