package com.appointmentapp.integration;

import com.appointmentapp.dto.*;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.enums.StatutPaiement;
import com.appointmentapp.domain.enums.RoleUser;
import com.appointmentapp.repository.PrestataireRepository;
import com.appointmentapp.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Appointment API
 * Tests the complete flow: client → service → appointment → payment
 * 
 * Run with: mvn verify -Dtest=AppointmentApiIntegrationTest
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AppointmentApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PrestataireRepository prestataireRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    private String adminAuth = "admin:admin123";
    private static final String API_BASE = "/api";
    private static long emailSequence = 0;
    private Long fixturePrestataireId;
    private Long fixtureServiceId;

    @BeforeEach
    public void setUp() {
        Prestataire prestataire = new Prestataire();
        prestataire.setEmail(nextEmail("prestataire"));
        prestataire.setNom("Prestataire Fixture");
        prestataire.setPassword("password123");
        prestataire.setTelephone("0601111111");
        prestataire.setRole(RoleUser.PRESTATAIRE);
        prestataire.setStatut("ACTIF");
        prestataire.setEstSupprime(false);
        prestataire.setSecteur("Coiffure");
        prestataire.setLocalisation("Paris");
        prestataire.setHoraires("09:00-18:00");
        Prestataire savedPrestataire = prestataireRepository.save(prestataire);
        fixturePrestataireId = savedPrestataire.getId();

        Service service = new Service();
        service.setNom("Service Fixture");
        service.setDescription("Service de test");
        service.setStatut("ACTIF");
        service.setDuree(45);
        service.setPrix(35.0);
        service.setEstDisponible(true);
        service.setEstSupprime(false);
        service.setPrestataire(savedPrestataire);
        Service savedService = serviceRepository.save(service);
        fixtureServiceId = savedService.getId();
    }

    private String nextEmail(String prefix) {
        emailSequence++;
        return prefix + emailSequence + "@example.com";
    }

    /**
     * Test 1: Create a Client
     */
    @Test
    public void testCreateClient() {
        ClientCreateDTO clientDTO = new ClientCreateDTO();
        clientDTO.setEmail("test-client@example.com");
        clientDTO.setNom("Test Client");
        clientDTO.setPassword("password123");
        clientDTO.setTelephone("0600000001");
        clientDTO.setAdresse("123 Test Street");
        clientDTO.setDateNaissance(LocalDate.of(1995, 5, 15));

        ResponseEntity<ClientDTO> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(API_BASE + "/clients", clientDTO, ClientDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("test-client@example.com", response.getBody().getEmail());
    }

    /**
     * Test 2: Retrieve all Clients (GET, no auth required)
     */
    @Test
    public void testGetAllClients() {
        ResponseEntity<ClientDTO[]> response = restTemplate
                .getForEntity(API_BASE + "/clients", ClientDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test 3: Create a Service
     */
    @Test
    public void testCreateService() {
        ServiceCreateDTO serviceDTO = new ServiceCreateDTO();
        serviceDTO.setNom("Hair Cut");
        serviceDTO.setDescription("Professional hair cutting service");
        serviceDTO.setPrix(50.0);
        serviceDTO.setDuree(60);
        serviceDTO.setEstDisponible(true);
        serviceDTO.setPrestataireId(fixturePrestataireId);

        ResponseEntity<ServiceDTO> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(API_BASE + "/services", serviceDTO, ServiceDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Hair Cut", response.getBody().getNom());
        assertEquals(fixturePrestataireId, response.getBody().getPrestataireId());
    }

    /**
     * Test 4: Retrieve all Services (GET, no auth required)
     */
    @Test
    public void testGetAllServices() {
        ResponseEntity<ServiceDTO[]> response = restTemplate
                .getForEntity(API_BASE + "/services", ServiceDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test 5: Validate that POST without auth is rejected
     */
    @Test
    public void testPostWithoutAuthenticationIsRejected() {
        ClientCreateDTO clientDTO = new ClientCreateDTO();
        clientDTO.setEmail("unauthorized@example.com");
        clientDTO.setNom("Unauthorized User");
        clientDTO.setPassword("password123");
        clientDTO.setTelephone("0600000002");
        clientDTO.setAdresse("456 Test St");
        clientDTO.setDateNaissance(LocalDate.of(2000, 1, 1));

        ResponseEntity<ClientDTO> response = restTemplate
                .postForEntity(API_BASE + "/clients", clientDTO, ClientDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Test 6: Create a Client with invalid email (validation error)
     */
    @Test
    public void testCreateClientWithInvalidEmail() {
        ClientCreateDTO clientDTO = new ClientCreateDTO();
        clientDTO.setEmail("invalid-email"); // Invalid email format
        clientDTO.setNom("Invalid Client");
        clientDTO.setPassword("password123");
        clientDTO.setTelephone("0600000003");
        clientDTO.setAdresse("789 Test Ave");
        clientDTO.setDateNaissance(LocalDate.of(1990, 6, 20));

        ResponseEntity<Object> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(API_BASE + "/clients", clientDTO, Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test 7: Create a Creneau
     */
    @Test
    public void testCreateCreneau() {
        CreneauCreateDTO creneauDTO = new CreneauCreateDTO();
        creneauDTO.setDateDebut(LocalDateTime.of(2026, 6, 1, 10, 0));
        creneauDTO.setDateFin(LocalDateTime.of(2026, 6, 1, 11, 0));
        creneauDTO.setPrestataireId(fixturePrestataireId);
        creneauDTO.setServiceId(fixtureServiceId);

        ResponseEntity<Object> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(API_BASE + "/creneaux", creneauDTO, Object.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test 8: Retrieve Payments (GET, no auth required)
     */
    @Test
    public void testGetAllPayments() {
        ResponseEntity<Object[]> response = restTemplate
                .getForEntity(API_BASE + "/paiements", Object[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test 9: Retrieve Reviews (GET, no auth required)
     */
    @Test
    public void testGetAllReviews() {
        ResponseEntity<Object[]> response = restTemplate
                .getForEntity(API_BASE + "/avis", Object[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test 10: Test that invalid status in payment update returns 400
     */
    @Test
    public void testUpdatePaymentWithInvalidStatus() {
        ResponseEntity<Object> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .exchange(
                        API_BASE + "/paiements/999/status?newStatus=INVALID",
                        HttpMethod.PATCH,
                        HttpEntity.EMPTY,
                        Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test 11: Test security config allows GET /api/**
     */
    @Test
    public void testGetRequestsArePublic() {
        // All GET endpoints should be accessible without auth
        ResponseEntity<Object[]> clientsResponse = restTemplate
                .getForEntity(API_BASE + "/clients", Object[].class);
        assertEquals(HttpStatus.OK, clientsResponse.getStatusCode());

        ResponseEntity<Object[]> servicesResponse = restTemplate
                .getForEntity(API_BASE + "/services", Object[].class);
        assertEquals(HttpStatus.OK, servicesResponse.getStatusCode());

        ResponseEntity<Object[]> paiementsResponse = restTemplate
                .getForEntity(API_BASE + "/paiements", Object[].class);
        assertEquals(HttpStatus.OK, paiementsResponse.getStatusCode());
    }

    /**
     * Test 12: Create User (System Admin)
     */
    @Test
    public void testCreateUser() {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setEmail("admin-user@example.com");
        userDTO.setNom("Admin User");
        userDTO.setPassword("securepass123");
        userDTO.setTelephone("0600000099");
        userDTO.setRole(RoleUser.ADMIN);
        userDTO.setStatut("ACTIF");

        ResponseEntity<UserDTO> response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(API_BASE + "/users", userDTO, UserDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    /**
     * Test 13: Retrieve User by ID (GET, no auth)
     */
    @Test
    public void testGetUserById() {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setEmail(nextEmail("lookup-user"));
        userDTO.setNom("Lookup User");
        userDTO.setPassword("securepass123");
        userDTO.setTelephone("0600000088");
        userDTO.setRole(RoleUser.ADMIN);
        userDTO.setStatut("ACTIF");

        ResponseEntity<UserDTO> createResponse = restTemplate
            .withBasicAuth("admin", "admin123")
            .postForEntity(API_BASE + "/users", userDTO, UserDTO.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());

        Long createdUserId = createResponse.getBody().getId();
        ResponseEntity<UserDTO> getResponse = restTemplate
            .getForEntity(API_BASE + "/users/" + createdUserId, UserDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(userDTO.getEmail(), getResponse.getBody().getEmail());
    }

    /**
     * Test 14: Test API Health endpoint (should always be public)
     */
    @Test
    public void testHealthEndpoint() {
        ResponseEntity<Map> response = restTemplate
            .getForEntity(API_BASE + "/health", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
    }
}
