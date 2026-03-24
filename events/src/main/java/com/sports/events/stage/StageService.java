package com.sports.events.stage;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {

    private final StageRepository repo;


    public StageService(StageRepository repo) {
        this.repo = repo;
    }

    public List<Stage> getAllStages() {
        return repo.findAll();
    }
}

