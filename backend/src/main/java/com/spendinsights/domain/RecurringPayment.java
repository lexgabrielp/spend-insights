package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "recurring_payments")
@Getter
@Setter
@NoArgsConstructor
public class RecurringPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    String merchant;
    BigDecimal averageAmount;
    String cadence;
    LocalDate nextExpectedAt;
    Double confidence;
    boolean active = true;
}
