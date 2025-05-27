package com.example.demo.repository;

import com.example.demo.model.UserPurchaseReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserPurchaseRewardRepository extends JpaRepository<UserPurchaseReward, Integer> {
    Optional<UserPurchaseReward> findByUserIdAndRewardId(Integer userId, Integer rewardId);
    List<UserPurchaseReward> findByUserId(Integer userId);
}