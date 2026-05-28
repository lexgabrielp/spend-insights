package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "embeddings")
@Getter
@Setter
@NoArgsConstructor
public class Embedding {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    @Column(nullable = false)
    String entityType;
    @Column(nullable = false)
    UUID entityId;
    @Column(columnDefinition = "text")
    String content;
    @Column(name = "embedding", columnDefinition = "vector(768)", insertable = false, updatable = false)
    String embedding;
    Instant createdAt = Instant.now();
}
