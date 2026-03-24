package com.sports.events.match;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository repo;


    public MatchService(MatchRepository repo) {
        this.repo = repo;
    }

    public List<Match> searchMatches(LocalDate date, Long sportTypeId) {
        boolean hasDate = date != null;
        boolean hasSportType = sportTypeId != null;

        if (hasDate) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();

            if (hasSportType) {
                return repo.findByDateVenueBetweenAndSportTypeId(start, end, sportTypeId);
            }

            return repo.findByDateVenueBetween(start, end);
        }

        if (hasSportType) {
            return repo.findBySportTypeId(sportTypeId);
        }

        return repo.findAll();
    }
}
