package com.sports.events.match;

import com.sports.events.sport.SportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller

public class MatchController {
    private final MatchService matchService;
    private final SportService sportService;

    public MatchController(MatchService matchService, SportService sportService) {
        this.matchService = matchService;
        this.sportService = sportService;
    }

    @GetMapping("/matches")
    public String getAllMatches(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam(required = false) String sportTypeId,
            Model model) {

        Long parsedSportTypeId = (sportTypeId == null || sportTypeId.isBlank())
                ? null
                : Long.valueOf(sportTypeId);

        model.addAttribute("matches", matchService.searchMatches(date, parsedSportTypeId));
        model.addAttribute("sportTypes", sportService.getSports());
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedSportTypeId", parsedSportTypeId);

        return "matches";
    }

}
