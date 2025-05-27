package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_purchase_rewards")
public class UserPurchaseReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "reward_id")
    private Integer rewardId;

    @Column(name = "claimed_at")
    private LocalDateTime claimedAt;
}