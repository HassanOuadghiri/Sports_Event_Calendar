package com.sports.events.team;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for team operations.
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param id the team ID
     * @return the team entity
     * @throws RuntimeException if the team is not found
     */
    public Team getTeamById(Integer id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));
    }
}
