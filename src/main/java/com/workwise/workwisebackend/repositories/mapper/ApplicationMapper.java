package com.workwise.workwisebackend.repositories.mapper;

import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationDTO mapToDTO(Application application) {
        return new ApplicationDTO(
                application.getId(),
                application.getUser().getId(),
                application.getApplicationDate(),
                application.getStatus(),
                application.getJobOffer().getId()
        );
    }
}