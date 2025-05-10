package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    private Long rewardId;
    private String rewardName;
    private int redeemPoints;

    public Long getRewardId() {
        return rewardId;
    }
    public String getRewardName() {
        return rewardName;
    }
    public int getRedeemPoints() {
        return redeemPoints;
    }    
}
