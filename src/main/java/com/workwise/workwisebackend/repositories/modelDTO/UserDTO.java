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
    private LocalDate createdDate;
    private byte[] cv;
    private Role role;
}