package com.appointmentapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateDTO {
    @NotNull
    private Long userId;

    @NotBlank
    private String type;

    @NotBlank
    private String contenu;
}
