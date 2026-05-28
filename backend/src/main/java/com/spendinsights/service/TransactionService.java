package com.spendinsights.service;

import com.spendinsights.ai.OllamaClient;
import com.spendinsights.domain.*;
import com.spendinsights.dto.TransactionDtos.*;
import com.spendinsights.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository txs;
    private final CategoryRepository cats;
    private final OllamaClient ai;

    public List<TransactionResponse> list(User u) {
        return txs.findByUserIdOrderByPostedAtDesc(u.getId()).stream().map(this::toDto).toList();
    }

    public DashboardSummary dashboard(User u) {
        var all = txs.findByUserIdOrderByPostedAtDesc(u.getId());

        BigDecimal income = sum(all, TransactionType.INCOME);
        BigDecimal expense = sum(all, TransactionType.EXPENSE);

        Map<String, BigDecimal> by = all.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory() == null ? "Uncategorized" : t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));

        return new DashboardSummary(
                income,
                expense,
                income.subtract(expense),
                by,
                all.stream().limit(10).map(this::toDto).toList(),
                null
        );
    }

    public Map<String, String> aiSummary(User u) {
        var all = txs.findByUserIdOrderByPostedAtDesc(u.getId());

        if (all.isEmpty()) {
            return Map.of("summary", "Upload transactions to generate local AI insights.");
        }

        String txData = all.stream()
                .limit(80)
                .map(t -> t.getPostedAt()
                        + " " + safe(t.getMerchant())
                        + " " + t.getAmount()
                        + " " + (t.getCategory() == null ? "" : t.getCategory().getName()))
                .toList()
                .toString();

        String summary = ai.chat(
                "Summarize these finances in 5 concise bullet points. "
                        + "Focus on spending patterns, risk, subscriptions, and practical recommendations. "
                        + "Data: " + txData
        );

        return Map.of("summary", summary);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private BigDecimal sum(List<Transaction> l, TransactionType typ) {
        return l.stream()
                .filter(t -> t.getType() == typ)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public TransactionResponse toDto(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getPostedAt(),
                t.getMerchant(),
                t.getAmount(),
                t.getType().name(),
                t.getCategory() == null ? null : t.getCategory().getName(),
                t.getDescription(),
                t.getAiConfidence()
        );
    }

    @Transactional
    public void categorize(User u, Transaction t) {
        String prompt = "Classify transaction into one category: Food, Transport, Rent, Utilities, Software, Tax, Income, Shopping, Health, Travel, Other. Return category only. Transaction: "
                + t.getMerchant() + " " + t.getDescription() + " " + t.getAmount();

        String rawName = ai.chat(prompt).replaceAll("[^A-Za-z ]", "").trim();
        final String categoryName = rawName.isBlank() ? "Other" : rawName;

        Category c = cats.findFirstByUserIdAndNameIgnoreCase(u.getId(), categoryName)
                .orElseGet(() -> {
                    Category x = new Category();
                    x.setUser(u);
                    x.setName(categoryName);
                    return cats.save(x);
                });

        t.setCategory(c);
        t.setAiConfidence(.72);
        txs.save(t);
    }
}