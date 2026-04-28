package com.sports.events.match;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for exposing match data as JSON.
 * Complements the Thymeleaf MatchController by providing
 * a programmatic API at /api/v1/matches.
 */
@RestController
@RequestMapping("/api/v1/matches")
public class MatchRestController {

    private final MatchService matchService;

    public MatchRestController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.searchMatches(null, null);
    }
}
