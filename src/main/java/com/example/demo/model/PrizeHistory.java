package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "prize_history")
public class PrizeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Khóa chính tự động

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "prize_id", nullable = false)
    private Prize prize;

    @Column(name = "won_at")
    private Timestamp wonAt;

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Prize getPrize() {
        return prize;
    }

    public Timestamp getWonAt() {
        return wonAt;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setWonAt(Timestamp wonAt) {
        this.wonAt = wonAt;
    }
}
