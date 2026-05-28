package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    @Column(nullable = false)
    String name;
    String institution;
    String currency = "PHP";
    BigDecimal openingBalance = BigDecimal.ZERO;
}
