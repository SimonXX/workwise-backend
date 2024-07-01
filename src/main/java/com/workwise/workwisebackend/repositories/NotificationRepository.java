package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.support.utils.RecipientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Trova notifiche per ID destinatario e tipo destinatario (user o company)
    Page<Notification> findByRecipientIdAndRecipientType(Pageable pageable, Long recipientId, RecipientType recipientType);

    // Trova notifiche non lette per ID destinatario e tipo destinatario (user o company)
    Page<Notification> findByRecipientIdAndRecipientTypeAndIsReadFalse(Pageable pageable, Long recipientId, String recipientType);

}