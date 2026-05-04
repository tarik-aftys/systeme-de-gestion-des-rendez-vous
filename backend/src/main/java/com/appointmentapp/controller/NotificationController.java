package com.appointmentapp.controller;

import com.appointmentapp.dto.NotificationCreateDTO;
import com.appointmentapp.domain.Notification;
import com.appointmentapp.domain.User;
import com.appointmentapp.service.NotificationService;
import com.appointmentapp.repository.UserRepository;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(notificationService.obtenirNotifications(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Notification> send(@Valid @RequestBody NotificationCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        if (user == null) return ResponseEntity.badRequest().build();
        Notification n = notificationService.envoyer(user, dto.getType(), dto.getContenu());
        return ResponseEntity.status(HttpStatus.CREATED).body(n);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markRead(@PathVariable Long id) {
        // find and mark read via repository through service
        java.util.Optional<Notification> opt = notificationService.obtenirNotifications(null).stream().filter(n -> n.getId().equals(id)).findFirst();
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Notification updated = notificationService.marquerCommeLue(opt.get());
        return ResponseEntity.ok(updated);
    }
}
