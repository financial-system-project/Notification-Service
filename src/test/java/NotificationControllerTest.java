package com.financial.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financial.notificationservice.dto.NotificationRequest;
import com.financial.notificationservice.entity.Notification;
import com.financial.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void health_ShouldReturnUp() throws Exception {

        mockMvc.perform(get("/api/notifications/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void sendNotification_ShouldReturnCreated() throws Exception {

        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setType("BUDGET_ALERT");
        request.setMessage("Budget exceeded");
        request.setChannel("EMAIL");

        Notification notification = Notification.builder()
                .id(1L)
                .userId(1L)
                .type("BUDGET_ALERT")
                .message("Budget exceeded")
                .channel("EMAIL")
                .sentAt(LocalDateTime.of(2025, 1, 1, 10, 0))
                .build();

        when(notificationService.send(any(NotificationRequest.class)))
                .thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("BUDGET_ALERT"))
                .andExpect(jsonPath("$.message").value("Budget exceeded"))
                .andExpect(jsonPath("$.channel").value("EMAIL"));
    }
}