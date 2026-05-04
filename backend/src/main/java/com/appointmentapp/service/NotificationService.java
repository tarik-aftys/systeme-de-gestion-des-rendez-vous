package com.appointmentapp.service;

import com.appointmentapp.domain.Notification;
import com.appointmentapp.domain.User;
import com.appointmentapp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public Notification envoyer(User user, String type, String contenu) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setContenu(contenu);
        notification.setStatut("ENVOYEE");
        notification.setDateEnvoi(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> obtenirNotifications(User user) {
        return notificationRepository.findByUser(user);
    }

    public Notification marquerCommeLue(Notification notification) {
        notification.marquerCommeLue();
        return notificationRepository.save(notification);
    }
}
