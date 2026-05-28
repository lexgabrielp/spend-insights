package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "anomaly_reports")
@Getter
@Setter
@NoArgsConstructor
public class AnomalyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    @ManyToOne
    Transaction transaction;
    double score;
    String reason;
    String severity;
    Instant createdAt = Instant.now();
}
