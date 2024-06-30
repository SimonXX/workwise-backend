package com.workwise.workwisebackend.repositories.modelDTO;

import com.workwise.workwisebackend.entities.actors.Company;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime posteddate;
    private LocalDateTime expirydate;
    private Company company;
    private List<ApplicationDTO> applications;

}

