package com.sports.events.stage;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for stage operations.
 */
@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    /**
     * Retrieves a stage by its ID.
     *
     * @param id the stage ID (String primary key)
     * @return the stage entity
     * @throws RuntimeException if the stage is not found
     */
    public Stage getStageById(String id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found with ID: " + id));
    }
}
