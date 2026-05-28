package com.spendinsights.dto;

import java.math.*;
import java.time.*;
import java.util.*;

public class TransactionDtos {
    public record TransactionResponse(UUID id, LocalDate postedAt, String merchant, BigDecimal amount, String type,
                                      String category, String description, Double aiConfidence) {
    }

    public record DashboardSummary(BigDecimal income, BigDecimal expenses, BigDecimal net,
                                   Map<String, BigDecimal> byCategory, java.util.List<TransactionResponse> recent,
                                   String aiSummary) {
    }
}
