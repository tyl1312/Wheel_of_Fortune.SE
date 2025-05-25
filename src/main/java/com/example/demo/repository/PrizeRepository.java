package com.example.demo.repository;

import com.example.demo.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {}
