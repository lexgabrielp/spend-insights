package com.spendinsights.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne
    User user;
    @Column(nullable = false)
    String name;
    String color;
    String icon;
    boolean systemDefault;
}
