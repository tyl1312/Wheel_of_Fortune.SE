package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "customers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "full_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "points")
    private int points;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ")";
    }
}
