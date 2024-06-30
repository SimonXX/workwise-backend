package com.workwise.workwisebackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private Long recipientId;

    @Column(nullable = false)
    private String recipientType;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}