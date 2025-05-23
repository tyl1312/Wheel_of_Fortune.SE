package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    private String phoneNumber;
    private String fullName;
    private String gender;
    private Long totalSpent;
    private int spin;

    @JsonIgnore
    private String password;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " (user_id=" + userId +
                ", full name=" + fullName +
                ", phone number=" + phoneNumber +
                ", gender=" + gender +
                ", total spent=" + totalSpent +
                ", spin=" + spin +
                ")";
    }
}