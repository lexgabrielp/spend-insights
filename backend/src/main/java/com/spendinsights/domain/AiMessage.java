package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "ai_messages")
@Getter
@Setter
@NoArgsConstructor
public class AiMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    AiConversation conversation;
    @Column(nullable = false)
    String role;
    @Column(columnDefinition = "text")
    String content;
    Instant createdAt = Instant.now();
}
