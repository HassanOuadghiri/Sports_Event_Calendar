package com.sports.events.result;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository repo;


    public ResultService(ResultRepository repo) {
        this.repo = repo;
    }

    public List<Result> getAllResults() {
        return repo.findAll();
    }
}
