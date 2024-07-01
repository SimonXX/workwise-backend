package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public interface NotificationApi {

    @PostMapping("/new-job-offer")
    ResponseEntity<Void> notifyAllCandidatesNewJobOffer(@RequestBody JobOffer jobOffer);

    @GetMapping("/myNotification")
    Page<Notification> getAllMyNotifications(Pageable pageable, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}