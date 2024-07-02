package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.Notification;
import com.workwise.workwisebackend.support.utils.RecipientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByRecipientIdAndRecipientTypeOrderByCreatedAtDesc(Pageable pageable, Long recipientId, RecipientType recipientType);

    Page<Notification> findByRecipientIdAndRecipientTypeAndIsReadFalse(Pageable pageable, Long recipientId, String recipientType);

    Optional<Notification> findById(Long id);

    void deleteById(Long id);

    List<Notification> findAllByRecipientId(Long id);
}