package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_onetime_missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOnetimeMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "mission_id")
    private Integer missionId;
    
    @Column(name = "is_completed")
    private boolean isCompleted;
    
    @Column(name = "is_claimed")
    private boolean isClaimed;
    
    @Column(name = "current_progress")
    private long currentProgress;

    public UserOnetimeMission(Integer userId, Integer missionId, boolean isCompleted, boolean isClaimed, long currentProgress) {
        this.userId = userId;
        this.missionId = missionId;
        this.isCompleted = isCompleted;
        this.isClaimed = isClaimed;
        this.currentProgress = currentProgress;
    }
}