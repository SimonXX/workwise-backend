package com.workwise.workwisebackend.repositories.modelAuth;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}