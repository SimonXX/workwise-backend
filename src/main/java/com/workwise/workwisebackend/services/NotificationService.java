package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import com.workwise.workwisebackend.repositories.NotificationRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import com.workwise.workwisebackend.support.utils.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialsRepository;

    public void notifyAllCandidatesNewJobOffer(JobOffer jobOffer) {
        List<User> candidates = userRepository.findAll();

        for (User candidate : candidates) {
            Notification notification = new Notification();
            notification.setType("New JobOffer");
            notification.setMessage("Hi " + candidate.getFirstName() + ". A new job offer is available! Expand for more information ...");
            notification.setRecipientId(candidate.getId());
            notification.setRecipientType(RecipientType.CANDIDATE);
            notificationRepository.save(notification);
        }
    }

    public Page<Notification> getAllMyNotifications(Pageable pageable, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = userRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(user.getId() + ", " + RecipientType.CANDIDATE);

        return notificationRepository.findByRecipientIdAndRecipientTypeOrderByCreatedAtDesc(pageable, user.getId(), RecipientType.CANDIDATE);
    }

    public Notification markAsRead(Long notificationId, String email) {
        Credential credentials = credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = userRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new IllegalArgumentException("Job Offer not found"));

        if (!notification.getRecipientId().equals(user.getId())) {
            throw new RuntimeException("Notification does not belong to user");
        }
        notification.setIsRead(true);

        return notificationRepository.save(notification);
    }
}
