package com.workwise.workwisebackend.repositories.mapper;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import com.workwise.workwisebackend.repositories.modelDTO.JobOfferDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobOfferMapper {

    private final ApplicationMapper applicationMapper;

    public JobOfferMapper(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    public JobOfferDTO mapToDTO(JobOffer jobOffer) {
        List<ApplicationDTO> applicationDTOs = jobOffer.getApplications().stream()
                .map(applicationMapper::mapToDTO)
                .collect(Collectors.toList());

        return new JobOfferDTO(
                jobOffer.getId(),
                jobOffer.getTitle(),
                jobOffer.getDescription(),
                jobOffer.getLocation(),
                jobOffer.getPosteddate(),
                jobOffer.getExpirydate(),
                jobOffer.getCompany(),
                applicationDTOs
        );
    }
}
