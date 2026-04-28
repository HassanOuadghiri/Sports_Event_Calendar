package com.sports.events.stage;

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
@DisplayName("StageService Unit Tests")
class StageServiceTest {

    @Mock
    private StageRepository stageRepository;

    @InjectMocks
    private StageService stageService;

    @Test
    @DisplayName("getAllStages delegates to repository")
    void getAllStages_delegatesToRepository() {
        Stage s1 = new Stage("GROUP", "Group Stage", 1);
        Stage s2 = new Stage("QF", "Quarter Final", 3);
        when(stageRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Stage> stages = stageService.getAllStages();

        assertEquals(2, stages.size());
        assertEquals("GROUP", stages.get(0).getId());
        verify(stageRepository).findAll();
    }

    @Test
    @DisplayName("getStageById returns stage when found")
    void getStageById_found() {
        Stage stage = new Stage("R16", "Round of 16", 2);
        when(stageRepository.findById("R16")).thenReturn(java.util.Optional.of(stage));

        Stage result = stageService.getStageById("R16");

        assertEquals("Round of 16", result.getName());
    }

    @Test
    @DisplayName("getStageById throws when not found")
    void getStageById_notFound() {
        when(stageRepository.findById("INVALID")).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> stageService.getStageById("INVALID"));
    }
}
