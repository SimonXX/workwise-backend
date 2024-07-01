package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.JobOfferApi;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.repositories.modelDTO.JobOfferDTO;
import com.workwise.workwisebackend.services.JobOfferService;
import com.workwise.workwisebackend.services.NotificationService;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobOfferController implements JobOfferApi {

    @Autowired
    JobOfferService jobOfferService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Page<JobOfferDTO> getAllJobOffers(Pageable pageable) {
        return jobOfferService.getAllJobOffers(pageable);
    }

    @Override
    public Page<JobOfferDTO> getAllAvailablesJobOffers(Pageable pageable, String token) {
        String email = jwtUtils.extractJwtToken(token);
        return jobOfferService.getAllAvailablesJobOffers(pageable, email);
    }

    @Override
    public Page<JobOfferDTO> getMyJobOffers(Pageable pageable, String token) {
        String email = jwtUtils.extractJwtToken(token);
        return jobOfferService.getMyJobOffers(pageable, email);
    }

    @Override
    public ResponseEntity<JobOffer> addJobOffer(JobOffer jobOffer, String token) {
        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT
        JobOffer createdJobOffer = jobOfferService.addJobOffer(jobOffer, email);
        notificationService.notifyAllCandidatesNewJobOffer(jobOffer);
        return ResponseEntity.ok(createdJobOffer);}

    @Override
    public ResponseEntity<JobOffer> modifyJobOffer(JobOffer jobOffer, String token) {
        String email = jwtUtils.extractJwtToken(token);
        JobOffer modifiedJobOffer = jobOfferService.modifyJobOffer(jobOffer, email);
        return ResponseEntity.ok(modifiedJobOffer);    }

    @Override
    public ResponseEntity<JobOfferDTO> deleteJobOffer(Long jobOfferId, String token) {
        String email = jwtUtils.extractJwtToken(token);
        JobOfferDTO deletedJobOffer = jobOfferService.deleteJobOffer(jobOfferId, email);
        return ResponseEntity.ok(deletedJobOffer);
    }
}
