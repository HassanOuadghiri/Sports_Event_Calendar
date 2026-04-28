package com.sports.events.match;

import com.sports.events.result.ResultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MatchService Unit Tests")
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    @DisplayName("searchMatches with no filters returns all matches")
    void searchMatches_noFilters_returnsAll() {
        List<Match> expected = List.of(new Match(), new Match());
        when(matchRepository.findAll()).thenReturn(expected);

        List<Match> result = matchService.searchMatches(null, null);

        assertEquals(expected, result);
        verify(matchRepository).findAll();
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    @DisplayName("searchMatches with date only filters by date range")
    void searchMatches_dateOnly_filtersByDate() {
        LocalDate date = LocalDate.of(2026, 3, 20);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Match> expected = List.of(new Match());
        when(matchRepository.findByDateVenueBetween(start, end)).thenReturn(expected);

        List<Match> result = matchService.searchMatches(date, null);

        assertEquals(expected, result);
        verify(matchRepository).findByDateVenueBetween(start, end);
    }

    @Test
    @DisplayName("searchMatches with sportTypeId only filters by sport type")
    void searchMatches_sportTypeOnly_filtersBySportType() {
        Long sportTypeId = 1L;
        List<Match> expected = List.of(new Match());
        when(matchRepository.findBySportTypeId(sportTypeId)).thenReturn(expected);

        List<Match> result = matchService.searchMatches(null, sportTypeId);

        assertEquals(expected, result);
        verify(matchRepository).findBySportTypeId(sportTypeId);
    }

    @Test
    @DisplayName("searchMatches with both filters uses combined query")
    void searchMatches_bothFilters_usesCombinedQuery() {
        LocalDate date = LocalDate.of(2026, 3, 20);
        Long sportTypeId = 1L;
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Match> expected = List.of(new Match());
        when(matchRepository.findByDateVenueBetweenAndSportTypeId(start, end, sportTypeId))
                .thenReturn(expected);

        List<Match> result = matchService.searchMatches(date, sportTypeId);

        assertEquals(expected, result);
        verify(matchRepository).findByDateVenueBetweenAndSportTypeId(start, end, sportTypeId);
    }

    @Test
    @DisplayName("searchMatches returns empty list when no matches found")
    void searchMatches_noResults_returnsEmptyList() {
        when(matchRepository.findAll()).thenReturn(List.of());

        List<Match> result = matchService.searchMatches(null, null);

        assertTrue(result.isEmpty());
    }
}
