package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.NotificationRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import com.workwise.workwisebackend.support.utils.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository; // Dipendenza per accedere agli utenti (candidati)

    public void notifyAllCandidatesNewJobOffer(JobOffer jobOffer) {
        // Recupera tutti gli utenti candidati
        List<User> candidates = userRepository.findAll();

        // Crea una notifica per ciascun candidato
        for (User candidate : candidates) {
            Notification notification = new Notification();
            notification.setType("New JobOffer");
            notification.setMessage("Hi " + candidate.getFirstName() + ". A new job offer is available! Expand for more information ...");
            notification.setRecipientId(candidate.getId());
            notification.setRecipientType(RecipientType.CANDIDATE);
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }

    // Altri metodi per gestire notifiche, segnare lette/non lette, ecc.
}
