package com.appointmentapp.service;

import com.appointmentapp.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Service/Prestation management
 * Handles business logic related to available services
 */
@Service
@RequiredArgsConstructor
public class AppointmentServiceService {
    
    private final ServiceRepository serviceRepository;
    
    /**
     * Find all available services
     */
    public List<com.appointmentapp.domain.Service> findAll() {
        return serviceRepository.findAll();
    }
    
    /**
     * Find all available services that are not deleted
     */
    public List<com.appointmentapp.domain.Service> findAllAvailable() {
        return serviceRepository.findByEstDisponibleIsTrueAndEstSupprimeIsFalse();
    }
    
    /**
     * Find a service by ID
     */
    public Optional<com.appointmentapp.domain.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }
    
    /**
     * Find services by name (partial match)
     */
    public List<com.appointmentapp.domain.Service> findByNom(String nom) {
        return serviceRepository.findByNomContainingIgnoreCase(nom);
    }
    
    /**
     * Save or update a service
     */
    public com.appointmentapp.domain.Service save(com.appointmentapp.domain.Service service) {
        return serviceRepository.save(service);
    }
    
    /**
     * Delete a service (soft delete)
     */
    public void delete(Long id) {
        serviceRepository.findById(id).ifPresent(service -> {
            service.setEstSupprime(true);
            serviceRepository.save(service);
        });
    }
    
    /**
     * Update service availability
     */
    public com.appointmentapp.domain.Service updateAvailability(Long id, Boolean isAvailable) {
        Optional<com.appointmentapp.domain.Service> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            service.get().setEstDisponible(isAvailable);
            return serviceRepository.save(service.get());
        }
        throw new RuntimeException("Service not found with id: " + id);
    }
}
