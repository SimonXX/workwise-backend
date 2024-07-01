package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.JobOffer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public interface NotificationApi {

    @PostMapping("/new-job-offer")
    ResponseEntity<Void> notifyAllCandidatesNewJobOffer(@RequestBody JobOffer jobOffer);
}