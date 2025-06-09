package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_daily_missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDailyMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "mission_id")
    private Integer missionId;
    
    @Column(name = "mission_date")
    private LocalDate missionDate;
    
    @Column(name = "is_completed")
    private boolean isCompleted;
    
    @Column(name = "is_claimed")
    private boolean isClaimed;
    
    // Custom constructor without ID (for new entities)
    public UserDailyMission(Integer userId, Integer missionId, LocalDate missionDate, 
                           boolean isCompleted, boolean isClaimed) {
        this.userId = userId;
        this.missionId = missionId;
        this.missionDate = missionDate;
        this.isCompleted = isCompleted;
        this.isClaimed = isClaimed;
    }
}