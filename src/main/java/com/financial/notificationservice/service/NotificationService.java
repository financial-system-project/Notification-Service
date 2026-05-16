package com.financial.notificationservice.service;

import com.financial.notificationservice.dto.NotificationRequest;
import com.financial.notificationservice.entity.Notification;
import com.financial.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    // FIX: Injected so every notification is persisted and queryable via GET endpoints
    private final NotificationRepository notificationRepository;

    public Notification send(NotificationRequest request) {
        log.info("[NOTIFICATION] type={} userId={} channel={} | {}",
                request.getType(), request.getUserId(),
                request.getChannel(), request.getMessage());

        // FIX: Persist the notification — in real life you would also call
        // an email/SMS provider here before saving
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .message(request.getMessage())
                .channel(request.getChannel() != null ? request.getChannel() : "EMAIL")
                .status("SENT")
                .sentAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public List<Notification> getByUser(Long userId) {
        return notificationRepository.findByUserIdOrderBySentAtDesc(userId);
    }
}