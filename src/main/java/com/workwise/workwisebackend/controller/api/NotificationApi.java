package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.services.NotificationService;
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

    @GetMapping("/myNotifications")
    Page<Notification> getAllMyNotifications(Pageable pageable, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PutMapping("/markAsRead/{idNotification}")
    Notification modifyNotification(@PathVariable Long idNotification, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}