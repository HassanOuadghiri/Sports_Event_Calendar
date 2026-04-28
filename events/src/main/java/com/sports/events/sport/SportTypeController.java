package com.sports.events.sport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for exposing sport type data as JSON.
 */
@RestController
@RequestMapping("/api/v1/sport-types")
public class SportTypeController {

    private final SportService sportService;

    public SportTypeController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping
    public List<SportType> getAllSportTypes() {
        return sportService.getSports();
    }
}
