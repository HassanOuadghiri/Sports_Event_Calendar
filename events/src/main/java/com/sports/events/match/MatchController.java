package com.sports.events.match;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public String getAllMatches(Model model){
        List<Match> matches = matchService.getAllMatches();
        model.addAttribute("matches", matches);
        return  "matches";
    }
}
