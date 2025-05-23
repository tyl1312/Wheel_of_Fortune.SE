package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    private Long rewardId;
    private String rewardName;
    @Column(name = "reward_points")
    private int rewardPoints;
}
