package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "prize_history")
@Getter
@Setter
public class PrizeHistory {

    @EmbeddedId
    private PrizeHistoryKey id;

    @ManyToOne
    @MapsId("userId") // ánh xạ userId từ EmbeddedId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("prizeId") // ánh xạ prizeId từ EmbeddedId
    @JoinColumn(name = "prize_id")
    private Prize prize;
}
