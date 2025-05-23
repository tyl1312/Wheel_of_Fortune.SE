package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private int userId;
    private String phoneNumber;
    private String fullName;
    private String gender;
    private Long totalSpent;
    private int spin;
}
