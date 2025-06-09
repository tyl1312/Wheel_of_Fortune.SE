package com.example.demo.repository;

import com.example.demo.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Integer> {
    Optional<Prize> findByPrizeDescription(String prizeDescription);
}


