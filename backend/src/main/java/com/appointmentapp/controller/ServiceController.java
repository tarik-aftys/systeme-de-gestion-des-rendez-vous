package com.appointmentapp.controller;

import com.appointmentapp.dto.ServiceDTO;
import com.appointmentapp.domain.Service;
import com.appointmentapp.service.AppointmentServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Service management
 * Handles HTTP requests related to appointment services/prestations
 * Base URL: /api/services
 */
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    
    private final AppointmentServiceService serviceService;
    
    /**
     * GET: Retrieve all services
     * @return List of all services
     */
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<Service> services = serviceService.findAll();
        List<ServiceDTO> dtos = services.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve all available services
     * @return List of available services only
     */
    @GetMapping("/available")
    public ResponseEntity<List<ServiceDTO>> getAvailableServices() {
        List<Service> services = serviceService.findAllAvailable();
        List<ServiceDTO> dtos = services.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific service by ID
     * @param id Service ID
     * @return Service details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        return serviceService.findById(id)
                .map(service -> ResponseEntity.ok(convertToDTO(service)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET: Search services by name
     * @param name Service name (partial match)
     * @return List of matching services
     */
    @GetMapping("/search")
    public ResponseEntity<List<ServiceDTO>> searchServicesByName(@RequestParam String name) {
        List<Service> services = serviceService.findByNom(name);
        List<ServiceDTO> dtos = services.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * POST: Create a new service
     * @param createDTO Service creation data
     * @return Created service with 201 status
     */
    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@Valid @RequestBody ServiceDTO createDTO) {
        Service service = new Service();
        service.setNom(createDTO.getNom());
        service.setDescription(createDTO.getDescription());
        service.setPrix(createDTO.getPrix());
        service.setDuree(createDTO.getDuree());
        service.setEstDisponible(createDTO.getEstDisponible() != null ? createDTO.getEstDisponible() : true);
        service.setEstSupprime(false);
        
        Service savedService = serviceService.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedService));
    }
    
    /**
     * PUT: Update an existing service
     * @param id Service ID
     * @param updateDTO Updated service data
     * @return Updated service or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceDTO updateDTO) {
        
        return serviceService.findById(id)
                .map(service -> {
                    service.setNom(updateDTO.getNom());
                    service.setDescription(updateDTO.getDescription());
                    service.setPrix(updateDTO.getPrix());
                    service.setDuree(updateDTO.getDuree());
                    Service updated = serviceService.save(service);
                    return ResponseEntity.ok(convertToDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * PATCH: Update service availability
     * @param id Service ID
     * @param available Availability status
     * @return Updated service or 404 if not found
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<ServiceDTO> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        
        try {
            Service updated = serviceService.updateAvailability(id, available);
            return ResponseEntity.ok(convertToDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE: Delete a service (soft delete)
     * @param id Service ID
     * @return 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Helper method: Convert Service entity to DTO
     */
    private ServiceDTO convertToDTO(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setNom(service.getNom());
        dto.setDescription(service.getDescription());
        dto.setPrix(service.getPrix());
        dto.setDuree(service.getDuree());
        dto.setEstDisponible(service.getEstDisponible());
        return dto;
    }
}
