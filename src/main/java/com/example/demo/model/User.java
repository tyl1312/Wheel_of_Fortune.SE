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
<<<<<<< HEAD
    private int user_id;
    private String email;
    private String full_name;
=======
    private int userId;
    private String phoneNumber;
    private String fullName;
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
    private String gender;
    private Long totalSpent;
    private int spin;

    @JsonIgnore
    private String password;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
<<<<<<< HEAD
                " (user_id=" + user_id +
                ", full name=" + full_name +
                ", email=" + email +
=======
                " (user_id=" + userId +
                ", full name=" + fullName +
                ", phone number=" + phoneNumber +
>>>>>>> 83943d58ce46ab7842cb02fe19cf687c8500275d
                ", gender=" + gender +
                ", total spent=" + totalSpent +
                ", spin=" + spin +
                ")";
    }
}