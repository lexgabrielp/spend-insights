package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "transactions", indexes = {@Index(name = "idx_tx_user_date", columnList = "user_id,postedAt"), @Index(name = "idx_tx_merchant", columnList = "merchant")})
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    @ManyToOne
    Account account;
    @ManyToOne
    Category category;
    @ManyToOne
    Upload upload;
    @Column(nullable = false)
    LocalDate postedAt;
    String merchant;
    @Column(nullable = false, precision = 14, scale = 2)
    BigDecimal amount;
    @Enumerated(EnumType.STRING)
    TransactionType type = TransactionType.EXPENSE;
    String currency = "PHP";
    String description;
    String source;
    Double aiConfidence;
    Instant createdAt = Instant.now();
}
