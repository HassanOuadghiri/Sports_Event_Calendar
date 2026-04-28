package com.sports.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("REST API Integration Tests")
class RestEndpointIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET /api/v1/result returns JSON list of 3 seeded results")
    void resultEndpoint_returnsJson() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/result", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().startsWith("["));
    }

    @Test
    @DisplayName("GET /api/v1/stages returns JSON list of 3 seeded stages")
    void stagesEndpoint_returnsJson() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/stages", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Group Stage"));
    }

    @Test
    @DisplayName("GET /api/v1/teams returns JSON list of 4 seeded teams")
    void teamsEndpoint_returnsJson() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/teams", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Portugal"));
    }

    @Test
    @DisplayName("GET /api/v1/matches returns JSON list of 3 seeded matches")
    void matchesEndpoint_returnsJson() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/matches", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().startsWith("["));
    }

    @Test
    @DisplayName("GET /api/v1/sport-types returns JSON list of 2 seeded sport types")
    void sportTypesEndpoint_returnsJson() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/sport-types", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Football"));
    }
}
