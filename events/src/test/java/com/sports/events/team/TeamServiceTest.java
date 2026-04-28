package com.sports.events.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeamService Unit Tests")
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("getAllTeams delegates to repository")
    void getAllTeams_delegatesToRepository() {
        Team t1 = new Team(1, "Portugal", "Portugal National Team", "portugal", "POR", "PT", 1);
        Team t2 = new Team(2, "Spain", "Spain National Team", "spain", "ESP", "ES", 2);
        when(teamRepository.findAll()).thenReturn(List.of(t1, t2));

        List<Team> teams = teamService.getAllTeams();

        assertEquals(2, teams.size());
        assertEquals("Portugal", teams.get(0).getName());
        verify(teamRepository).findAll();
    }

    @Test
    @DisplayName("getTeamById returns team when found")
    void getTeamById_found() {
        Team team = new Team(1, "France", "France National Team", "france", "FRA", "FR", 1);
        when(teamRepository.findById(1)).thenReturn(Optional.of(team));

        Team result = teamService.getTeamById(1);

        assertEquals("France", result.getName());
    }

    @Test
    @DisplayName("getTeamById throws when not found")
    void getTeamById_notFound() {
        when(teamRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teamService.getTeamById(999));
    }
}
