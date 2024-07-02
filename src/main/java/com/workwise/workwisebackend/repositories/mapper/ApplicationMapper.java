package com.workwise.workwisebackend.repositories.mapper;

import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import com.workwise.workwisebackend.repositories.modelDTO.JobOfferSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationDTO mapToDTO(Application application) {
        JobOfferSummaryDTO jobOfferSummaryDTO = mapToSummaryDTO(application.getJobOffer());

        return new ApplicationDTO(
                application.getId(),
                application.getUser().getId(),
                application.getApplicationDate(),
                application.getStatus(),
                jobOfferSummaryDTO
        );
    }
    private JobOfferSummaryDTO mapToSummaryDTO(JobOffer jobOffer) {
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