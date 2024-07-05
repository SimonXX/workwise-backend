package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.*;
import com.workwise.workwisebackend.repositories.mapper.ApplicationMapper;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CredentialRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobOfferRepository jobOfferRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private CompanyRepository companyRepository;

    public List<ApplicationDTO> getAllApplications() {

        List<Application> applications = applicationRepository.findAll();

        return applications.stream()
                .map(applicationMapper::mapToDTO) // Utilizza il mapper per mappare Application a ApplicationDTO
                .collect(Collectors.toList());
    }

    public Page<ApplicationDTO> getMyApplications(Pageable pageable, String email){

        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credentials not found for email: " + email));

        Optional<User> optionalUser = userRepository.findByCredentials(credentials.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Application> applications = applicationRepository.findByUserId(pageable, user.getId());
            return applications.map(applicationMapper::mapToDTO);
        } else {
            Optional<Company> optionalCompany = companyRepository.findByCredentials(credentials.getId());

            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                Page<Application> applications = applicationRepository.findApplicationsByCompanyId(pageable, company.getId());

                return applications.map(applicationMapper::mapToDTO);
            }
        }
        throw new RuntimeException("No user or company found for the provided credentials");
     }


    public ApplicationDTO addApplication(Long jobOffer, String email) {

        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credentials not found for email: " + email));

        User user = userRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<JobOffer> jobOfferOptional = jobOfferRepository.findById(jobOffer);

        if (jobOfferOptional.isEmpty()) {
            throw new InvalidConfigurationPropertyValueException("Job offer not found", "application.jobOffer", jobOffer.toString());
        }



        Optional<Application> existingApplicationOptional = applicationRepository.findByUserIdAndJobOfferId(user.getId(), jobOffer);
        if (existingApplicationOptional.isPresent()) {
            throw new RuntimeException("Application already exists for this user and job offer");
        }

        Application newApp = new Application();
        newApp.setJobOffer(jobOfferOptional.get());
        newApp.setUser(user);
        newApp.setApplicationDate(LocalDateTime.now());
        newApp.setStatus("Pending");

        Application saved = applicationRepository.save(newApp);


        return applicationMapper.mapToDTO(saved);
    }


    @Transactional
    public ApplicationDTO deleteApplication(Long idApplication, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = userRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application application = applicationRepository.findById(idApplication)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denies");
        }

        applicationRepository.deleteById(application.getId());

        return applicationMapper.mapToDTO(application);

    }

    @Transactional
    public ApplicationDTO modifyApplication(Long idApplication, String newStatus, String email) {

        Application application = applicationRepository.findById(idApplication)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Optional<JobOffer> jobOffer = jobOfferRepository.findById(application.getJobOffer().getId());

        System.out.println(jobOffer.get().getCompany().getCredentials().getEmail());

        if (!jobOffer.get().getCompany().getCredentials().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        // Verifica e imposta il nuovo status
        switch (newStatus.toUpperCase()) {
            case "PENDING":
            case "ACCEPTED":
            case "REJECTED":
                application.setStatus(newStatus.toUpperCase());
                break;
            default:
                throw new RuntimeException("Invalid status: " + newStatus);
        }

        applicationRepository.save(application);

        return applicationMapper.mapToDTO(application);
    }
}
