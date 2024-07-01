package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.NotificationApi;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController implements NotificationApi {

    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<Void> notifyAllCandidatesNewJobOffer(@RequestBody JobOffer jobOffer) {
        notificationService.notifyAllCandidatesNewJobOffer(jobOffer);
        return ResponseEntity.ok().build();
    }
}
