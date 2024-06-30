package com.workwise.workwisebackend.repositories.modelAuth;

import lombok.Data;

@Data
public class CompanyRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String website;
    private String description;
}