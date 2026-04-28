package com.sports.events.match;

import com.sports.events.result.Result;
import com.sports.events.result.ResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for match-related business logic.
 * Encapsulates filtering/search logic and match creation.
 */
@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ResultRepository resultRepository;

    public MatchService(MatchRepository matchRepository, ResultRepository resultRepository) {
        this.matchRepository = matchRepository;
        this.resultRepository = resultRepository;
    }

    /**
     * Searches matches with optional date and sport type filters.
     * Supports four combinations: no filter, date only, sport only, both.
     *
     * @param date        optional date filter (matches on this day)
     * @param sportTypeId optional sport type ID filter
     * @return filtered list of matches
     */
    public List<Match> searchMatches(LocalDate date, Long sportTypeId) {
        boolean hasDate = date != null;
        boolean hasSportType = sportTypeId != null;

        if (hasDate) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();

            if (hasSportType) {
                return matchRepository.findByDateVenueBetweenAndSportTypeId(start, end, sportTypeId);
            }

            return matchRepository.findByDateVenueBetween(start, end);
        }

        if (hasSportType) {
            return matchRepository.findBySportTypeId(sportTypeId);
        }

        return matchRepository.findAll();
    }

    /**
     * Creates a new match along with its associated result.
     * The result is persisted first so the match can reference it.
     *
     * @param match  the match entity to save
     * @param result the result entity to save and associate with the match
     * @return the persisted match
     */
    public Match createMatch(Match match, Result result) {
        Result savedResult = resultRepository.save(result);
        match.setResult(savedResult);
        return matchRepository.save(match);
    }
}
