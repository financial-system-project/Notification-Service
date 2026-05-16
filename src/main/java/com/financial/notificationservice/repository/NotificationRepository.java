package com.financial.notificationservice.repository;

import com.financial.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// FIX: New repository — previously there was no persistence at all
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderBySentAtDesc(Long userId);

    List<Notification> findByTypeOrderBySentAtDesc(String type);
}