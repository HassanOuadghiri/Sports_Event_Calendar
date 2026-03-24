package com.sports.events.sport;

import com.sports.events.match.Match;
import com.sports.events.match.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SportService {

    private final SportRepository repo;


    public SportService(SportRepository repo) {
        this.repo = repo;
    }

    public List<SportType> getSports() {
        return repo.findAll();
    }
}
