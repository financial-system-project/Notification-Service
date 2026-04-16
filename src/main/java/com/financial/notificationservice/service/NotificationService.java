package com.financial.notificationservice.service;

import com.financial.notificationservice.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void send(NotificationRequest request) {
        log.info("Sending {} notification to user {} via {}: {}",
                request.getType(), request.getUserId(), request.getChannel(), request.getMessage());
        // In real implementation: send email, SMS, push notification
    }
}