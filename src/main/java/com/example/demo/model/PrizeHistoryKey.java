package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
@Data
public class PrizeHistoryKey implements Serializable {
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "prize_id")
    private Integer prizeId;
    
    @Column(name = "timestamp")
    private Timestamp timestamp;
}