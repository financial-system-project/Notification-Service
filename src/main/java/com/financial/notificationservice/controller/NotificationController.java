package com.financial.notificationservice.controller;

import com.financial.notificationservice.dto.NotificationRequest;
import com.financial.notificationservice.entity.Notification;
import com.financial.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "notification-service");
        response.put("status", "UP");
        return response;
    }

    // FIX: Now returns the saved Notification with its generated id + sentAt timestamp
    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest request) {
        Notification saved = notificationService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // FIX: New — fetch all sent notifications (proves alerts fired during the demo)
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAll();
    }

    // FIX: New — fetch notifications for a specific user
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable Long userId) {
        return notificationService.getByUser(userId);
    }
}