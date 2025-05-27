package com.example.demo.repository;

import com.example.demo.model.PurchaseReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRewardRepository extends JpaRepository<PurchaseReward, Integer> {
    List<PurchaseReward> findAllByOrderByRewardIdAsc();
}