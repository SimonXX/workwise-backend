package com.workwise.workwisebackend.repositories.modelDTO;

import com.workwise.workwisebackend.entities.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String cvBase64;
}