package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Trova notifiche per ID destinatario e tipo destinatario (user o company)
    List<Notification> findByRecipientIdAndRecipientType(Long recipientId, String recipientType);

    // Trova notifiche non lette per ID destinatario e tipo destinatario (user o company)
    List<Notification> findByRecipientIdAndRecipientTypeAndIsReadFalse(Long recipientId, String recipientType);

}