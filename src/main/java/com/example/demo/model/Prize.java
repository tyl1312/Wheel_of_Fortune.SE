package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prizes")
public class Prize {
    @Id
    private int prize_id;
    private String prize_description;
    private float probability;

    public int getPrizeId() {
        return prize_id;
    }
    public String getPrizeName() {
        return prize_description;
    }
    public float getPrizeProbability() {
        return probability;
    }
}
