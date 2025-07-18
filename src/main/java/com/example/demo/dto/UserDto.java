package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private int user_id;
    private String email;
    private String full_name;
    private String gender;
    private Long total_spent;
    private int spin;
}
