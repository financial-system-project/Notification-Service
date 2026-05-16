package com.financial.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// FIX: New entity — notifications are now persisted so the demo can prove they were sent
@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String type;      // e.g. BUDGET_ALERT

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private String channel;   // EMAIL, SMS, PUSH

    @Column(nullable = false)
    private String status;    // SENT, FAILED

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        if (sentAt == null) sentAt = LocalDateTime.now();
        if (status == null) status = "SENT";
    }
}