package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_missions")
public class UserMission {
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
    private Boolean isCompleted;
}