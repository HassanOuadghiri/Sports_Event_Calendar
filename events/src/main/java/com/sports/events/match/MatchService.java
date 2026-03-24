package com.sports.events.match;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository repo;


    public MatchService(MatchRepository repo) {
        this.repo = repo;
    }

    public List<Match> getAllMatches(){
        return repo.findAll();
    }
    public Match getMatchById(int id){
        return repo.findById(id).orElse(null);
    }
}
