package com.example.demo.repository;

import com.example.demo.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
    Optional<Prize> findByPrizeDescription(String prizeDescription);
}


