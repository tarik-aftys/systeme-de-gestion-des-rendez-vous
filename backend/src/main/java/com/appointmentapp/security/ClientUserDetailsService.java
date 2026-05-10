package com.appointmentapp.security;

import com.appointmentapp.domain.Client;
import com.appointmentapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log pour debug (regarde ta console Java lors de la connexion)
        System.out.println("Tentative de connexion pour : " + username);

        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }

        // On cherche par EMAIL car dans ton DTO LoginRequest, c'est l'email qui est envoyé
        return clientRepository.findByEmail(username)
                .map(client -> User.builder()
                        .username(client.getEmail())
                        .password(client.getPassword()) // Le hash récupéré en BDD
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_CLIENT")))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Email non trouvé : " + username));
    }
}