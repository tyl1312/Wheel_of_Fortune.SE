package com.example.demo.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String full_name;
    private Integer spin;
    private String password;
    private Long total_spent;
}
