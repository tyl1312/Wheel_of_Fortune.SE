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
    private int user_id;
    private String email;
    private String full_name;
    private String gender;
    private Long total_spent;
    private int spin;

    @JsonIgnore
    private String password;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " (user_id=" + user_id +
                ", full name=" + full_name +
                ", email=" + email +
                ", gender=" + gender +
                ", total spent=" + total_spent +
                ", spin=" + spin +
                ")";
    }
}