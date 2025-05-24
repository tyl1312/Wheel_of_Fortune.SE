package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
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
}
