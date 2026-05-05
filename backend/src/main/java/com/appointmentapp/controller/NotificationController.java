package com.appointmentapp.controller;

import com.appointmentapp.dto.NotificationCreateDTO;
import com.appointmentapp.domain.Notification;
import com.appointmentapp.domain.User;
import com.appointmentapp.service.NotificationService;
import com.appointmentapp.repository.UserRepository;
import com.appointmentapp.repository.NotificationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(notificationService.obtenirNotifications(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Notification> send(@Valid @RequestBody NotificationCreateDTO dto) {
        return userRepository.findById(dto.getUserId())
                .map(user -> {
                    Notification n = notificationService.envoyer(user, dto.getType(), dto.getContenu());
                    return ResponseEntity.status(HttpStatus.CREATED).body(n);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markRead(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(notification -> ResponseEntity.ok(notificationService.marquerCommeLue(notification)))
                .orElse(ResponseEntity.notFound().build());
    }
}
