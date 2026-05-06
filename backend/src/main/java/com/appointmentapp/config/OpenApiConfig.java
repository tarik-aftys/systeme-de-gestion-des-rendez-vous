package com.appointmentapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Appointment API",
                version = "1.0.0",
                description = "API de gestion des rendez-vous, clients, services et paiements",
                contact = @Contact(name = "Appointment App")
        ),
        servers = {
                @Server(url = "/", description = "Serveur local")
        }
)
public class OpenApiConfig {
}