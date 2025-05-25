package com.example.demo.repository;

import com.example.demo.model.PrizeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeHistoryRepository extends JpaRepository<PrizeHistory, Long> {
}
