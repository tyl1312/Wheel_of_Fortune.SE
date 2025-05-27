package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prizes")
public class Prize {
    @Id
    private int prize_id;
    @Column(name = "prize_description")
    private String prizeDescription;
    private float probability;

    public int getPrizeId() {
        return prize_id;
    }
    public String getPrizeName() {
        return prizeDescription;
    }
    public float getPrizeProbability() {
        return probability;
    }
}
