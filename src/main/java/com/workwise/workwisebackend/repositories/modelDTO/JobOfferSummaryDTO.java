package com.workwise.workwisebackend.repositories.modelDTO;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.actors.Company;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferSummaryDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime posteddate;
    private LocalDateTime expirydate;
    private Company company;

    public JobOfferSummaryDTO mapToSummaryDTO(JobOffer jobOffer) {
        return new JobOfferSummaryDTO(
                jobOffer.getId(),
                jobOffer.getTitle(),
                jobOffer.getDescription(),
                jobOffer.getLocation(),
                jobOffer.getPosteddate(),
                jobOffer.getExpirydate(),
                jobOffer.getCompany()
        );
    }

}