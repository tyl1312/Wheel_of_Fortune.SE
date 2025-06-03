package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int missionId;
    private String missionDescription;
    private int spinReward;

    @Column(name = "target_value") 
    private long targetValue;

    @Column(name = "mission_type")
    @Enumerated(EnumType.STRING)
    private MissionType missionType;
}

