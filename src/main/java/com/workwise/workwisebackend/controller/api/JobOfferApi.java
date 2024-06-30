package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.repositories.modelDTO.JobOfferDTO;
import com.workwise.workwisebackend.support.utils.EntityList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/joboffers")
public interface JobOfferApi {

    @Operation(summary = "Takes all existing joboffers", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "", produces = "application/json")
    Page<JobOfferDTO> getAllJobOffers(Pageable pageable);

    @Operation(summary = "The user only takes the Job Offers for which he has not yet applied", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/availablesJobOffers", produces = "application/json")
    Page<JobOfferDTO> getAllAvailablesJobOffers(Pageable pageable, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "takes all jobOffers related to a company", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/myJobOffers", produces = "application/json")
    Page<JobOfferDTO> getMyJobOffers(Pageable pageable, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);


    @Operation(summary = "Allows a company to add a job offer", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PostMapping(path = "/addJobOffer", produces = "application/json")
    ResponseEntity<JobOffer> addJobOffer(@RequestBody JobOffer jobOffer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);


    @Operation(summary = "allows a company to modify its job offer", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PutMapping(path = "/modifyJobOffer", produces = "application/json")
    ResponseEntity<JobOffer> modifyJobOffer(@Valid @RequestBody JobOffer jobOffer,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "allows a company to delete its job offer")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping(path = "/deleteJobOffer/{jobOfferId}", produces = "application/json")
    ResponseEntity<JobOfferDTO> deleteJobOffer(@PathVariable Long jobOfferId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

class JobOffersList extends EntityList<JobOffer> {}
