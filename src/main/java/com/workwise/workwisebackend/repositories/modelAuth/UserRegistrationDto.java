package com.workwise.workwisebackend.repositories.modelAuth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String cv;
}
