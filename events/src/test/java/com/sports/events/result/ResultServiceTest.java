package com.sports.events.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ResultService Unit Tests")
class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private ResultService resultService;

    @Test
    @DisplayName("getAllResults delegates to repository")
    void getAllResults_delegatesToRepository() {
        Result r1 = new Result(1, 2, 1, "HOME", "Home win", 3, 1, 0, 0);
        Result r2 = new Result(2, 0, 0, "DRAW", "Draw", 0, 0, 0, 0);
        when(resultRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Result> results = resultService.getAllResults();

        assertEquals(2, results.size());
        assertEquals("HOME", results.get(0).getWinner());
        verify(resultRepository).findAll();
    }

    @Test
    @DisplayName("getAllResults returns empty list when no results")
    void getAllResults_empty() {
        when(resultRepository.findAll()).thenReturn(List.of());

        List<Result> results = resultService.getAllResults();

        assertTrue(results.isEmpty());
    }
}
