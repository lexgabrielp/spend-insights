package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "uploads")
@Getter
@Setter
@NoArgsConstructor
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne(optional = false)
    User user;
    String filename;
    String contentType;
    long sizeBytes;
    @Enumerated(EnumType.STRING)
    UploadStatus status = UploadStatus.PENDING;
    String errorMessage;
    Instant createdAt = Instant.now();
}
