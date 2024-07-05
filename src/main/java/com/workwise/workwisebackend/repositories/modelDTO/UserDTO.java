package com.workwise.workwisebackend.repositories.modelDTO;

import com.workwise.workwisebackend.entities.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String cvBase64;  // Campo per il CV codificato in Base64
    private LocalDate createdDate;
    private Role role;
}