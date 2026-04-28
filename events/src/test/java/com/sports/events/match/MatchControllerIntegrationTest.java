package com.sports.events.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("MatchController Integration Tests")
class MatchControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET / returns home page")
    void homePage_returns200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("SportsCal"));
    }

    @Test
    @DisplayName("GET /matches returns matches page")
    void matchesPage_returns200WithData() {
        ResponseEntity<String> response = restTemplate.getForEntity("/matches", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Match"));
    }

    @Test
    @DisplayName("GET /matches with date filter returns 200")
    void matchesPage_dateFilter_returns200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/matches?date=2026-03-20", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /matches with sportTypeId filter returns 200")
    void matchesPage_sportTypeFilter_returns200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/matches?sportTypeId=1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /matches with both filters returns 200")
    void matchesPage_bothFilters_returns200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/matches?date=2026-03-20&sportTypeId=1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
