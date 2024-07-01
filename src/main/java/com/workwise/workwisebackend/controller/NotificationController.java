package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.NotificationApi;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.services.JobOfferService;
import com.workwise.workwisebackend.services.NotificationService;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController implements NotificationApi {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JWTUtils jwtUtils;



    public ResponseEntity<Void> notifyAllCandidatesNewJobOffer(@RequestBody JobOffer jobOffer) {
        notificationService.notifyAllCandidatesNewJobOffer(jobOffer);
        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Notification> getAllMyNotifications(Pageable pageable, String token) {
        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT

        return notificationService.getAllMyNotifications(pageable, email);
    }

    @Override
    public Notification modifyNotification(Long idNotification, String token) {
        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT
        return notificationService.markAsRead(idNotification, email);
    }

}
