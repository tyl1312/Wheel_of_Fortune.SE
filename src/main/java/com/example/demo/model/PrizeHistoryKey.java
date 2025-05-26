package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class PrizeHistoryKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "prize_id")
    private Integer prizeId;

    @Column(name = "won_at")
    private Timestamp wonAt;

    public PrizeHistoryKey() {
    }

    public PrizeHistoryKey(Integer userId, Integer prizeId, Timestamp wonAt) {
        this.userId = userId;
        this.prizeId = prizeId;
        this.wonAt = wonAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrizeHistoryKey)) return false;
        PrizeHistoryKey that = (PrizeHistoryKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(prizeId, that.prizeId) &&
                Objects.equals(wonAt, that.wonAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, prizeId, wonAt);
    }
}