package com.spendinsights.repository;

import com.spendinsights.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface AiMessageRepository extends JpaRepository<AiMessage, UUID> {
}
