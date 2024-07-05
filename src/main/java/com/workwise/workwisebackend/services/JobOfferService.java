package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.CompanyRepository;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import com.workwise.workwisebackend.repositories.JobOfferRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import com.workwise.workwisebackend.repositories.mapper.ApplicationMapper;
import com.workwise.workwisebackend.repositories.mapper.JobOfferMapper;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import com.workwise.workwisebackend.repositories.modelDTO.JobOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    JobOfferRepository jobOfferRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CredentialRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobOfferMapper jobOfferMapper;

    @Autowired
    ApplicationMapper applicationMapper;

    public Page<JobOfferDTO> getAllJobOffers(Pageable pageable){
        Page<JobOffer> jobOffers = jobOfferRepository.findAll(pageable);
        return jobOffers.map(jobOffer -> {
            List<ApplicationDTO> applicationDTOs = jobOffer.getApplications().stream()
                    .map(applicationMapper::mapToDTO)
                    .toList();
            return jobOfferMapper.mapToDTO(jobOffer);
        });
    }

    public Page<JobOfferDTO> getAllAvailablesJobOffers(Pageable pageable, String email){
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credentials not found for email: " + email));


        // Prova a trovare l'utente
        Optional<User> optionalUser = userRepository.findByCredentials(credentials.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<JobOffer> jobOffers = jobOfferRepository.findAvailablesJobOffers(pageable, user.getId());

            return jobOffers.map(jobOffer -> {
                List<ApplicationDTO> applicationDTOs = jobOffer.getApplications().stream()
                        .map(applicationMapper::mapToDTO)
                        .toList();

                return jobOfferMapper.mapToDTO(jobOffer);
            });
        } else {
            // Prova a trovare la compagnia se l'utente non è presente
            Optional<Company> optionalCompany = companyRepository.findByCredentials(credentials.getId());

            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                Page<JobOffer> jobOffers = jobOfferRepository.findByCompanyId(company.getId(), pageable);

                return jobOffers.map(jobOffer -> {
                    List<ApplicationDTO> applicationDTOs = jobOffer.getApplications().stream()
                            .map(applicationMapper::mapToDTO)
                            .toList();

                    return jobOfferMapper.mapToDTO(jobOffer);
                });
            } else {
                throw new RuntimeException("No user or company found for the provided credentials");
            }
        }
    }


    //usato sia da user che da company
    public Page<JobOfferDTO> getMyJobOffers(Pageable pageable, String email){
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Company company = companyRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Page<JobOffer> jobOffers = jobOfferRepository.findByCompanyId(company.getId(), pageable);

        return jobOffers.map(jobOfferMapper::mapToDTO);
    }


    public JobOffer addJobOffer(JobOffer jobOffer, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Company company = companyRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));


        jobOffer.setCompany(company);
        jobOffer.setPosteddate(LocalDateTime.now());
        jobOffer.setExpirydate(jobOffer.getExpirydate());

        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer modifyJobOffer(JobOffer jobOffer, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Company company = companyRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        JobOffer existingJobOffer = jobOfferRepository.findById(jobOffer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Job Offer not found"));

        if (!existingJobOffer.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("Access Denied");
        }

        existingJobOffer.setTitle(jobOffer.getTitle());
        existingJobOffer.setDescription(jobOffer.getDescription());
        existingJobOffer.setLocation(jobOffer.getLocation());
        existingJobOffer.setExpirydate(jobOffer.getExpirydate());

        return jobOfferRepository.save(existingJobOffer);
    }

    public JobOfferDTO deleteJobOffer (Long jobOfferId, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Company company = companyRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        JobOffer existingJobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new IllegalArgumentException("Job Offer not found"));

        if(!existingJobOffer.getCompany().getId().equals(company.getId())){
            throw new RuntimeException("Access Denies");
        }

        jobOfferRepository.delete(existingJobOffer);

        return jobOfferMapper.mapToDTO(existingJobOffer);
    }
}
