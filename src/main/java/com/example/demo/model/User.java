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
    
    @Builder.Default
    private Long total_spent = 0L;
    
    private int spin;

    @JsonIgnore
    private String password;

    @Column(name = "is_admin")
    @Builder.Default
    private Boolean isAdmin = false;

    // Getter that safely handles null and converts 0/1 to boolean
    public Boolean getIsAdmin() {
        return isAdmin != null ? isAdmin : false;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Convenience method for boolean checks
    public boolean isAdminUser() {
        return Boolean.TRUE.equals(isAdmin);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " (user_id=" + user_id +
                ", full name=" + full_name +
                ", email=" + email +
                ", gender=" + gender +
                ", total spent=" + total_spent +
                ", spin=" + spin +
                ", isAdmin=" + isAdmin +
                ")";
    }
}