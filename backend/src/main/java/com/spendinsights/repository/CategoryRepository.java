package com.spendinsights.repository;

import com.spendinsights.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findFirstByUserIdAndNameIgnoreCase(UUID userId, String name);
}
