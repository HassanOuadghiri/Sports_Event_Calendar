package com.sports.events.sport;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for sport type operations.
 */
@Service
public class SportService {

    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<SportType> getSports() {
        return sportRepository.findAll();
    }

    /**
     * Retrieves a sport type by its ID.
     *
     * @param id the sport type ID
     * @return the sport type entity
     * @throws RuntimeException if the sport type is not found
     */
    public SportType getSportById(Integer id) {
        return sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport type not found with ID: " + id));
    }
}
