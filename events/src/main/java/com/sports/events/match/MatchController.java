package com.sports.events.match;

import com.sports.events.result.Result;
import com.sports.events.sport.SportService;
import com.sports.events.stage.StageService;
import com.sports.events.team.TeamService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Thymeleaf controller for match-related views.
 * Handles the home page, match listing with filters, and event creation form.
 */
@Controller
public class MatchController {

    private final MatchService matchService;
    private final SportService sportService;
    private final TeamService teamService;
    private final StageService stageService;

    public MatchController(MatchService matchService,
                           SportService sportService,
                           TeamService teamService,
                           StageService stageService) {
        this.matchService = matchService;
        this.sportService = sportService;
        this.teamService = teamService;
        this.stageService = stageService;
    }

    /**
     * Home page — landing page of the application.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Displays all matches with optional date and sport type filters.
     * Also populates dropdown data for filters and the create-event form.
     */
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
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("stages", stageService.getAllStages());
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedSportTypeId", parsedSportTypeId);

        return "matches";
    }

    /**
     * Handles form submission to create a new match with its result.
     * Expects form fields for match details and result scores.
     */
    @PostMapping("/matches")
    public String createMatch(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateVenue,
            @RequestParam(required = false) String matchGroup,
            @RequestParam(required = false) String originCompetitionId,
            @RequestParam(required = false) String originCompetitionName,
            @RequestParam Integer season,
            @RequestParam Integer homeTeamId,
            @RequestParam Integer awayTeamId,
            @RequestParam(required = false) String stageId,
            @RequestParam(required = false) String stadium,
            @RequestParam(required = false) Integer sportTypeId,
            @RequestParam(defaultValue = "0") Integer homeGoals,
            @RequestParam(defaultValue = "0") Integer awayGoals,
            @RequestParam(required = false) String winner,
            @RequestParam(required = false) String message,
            RedirectAttributes redirectAttributes) {

        try {
            // Build the Result entity
            Result result = new Result();
            result.setHomeGoals(homeGoals);
            result.setAwayGoals(awayGoals);
            result.setWinner(winner);
            result.setMessage(message);
            result.setGoals(homeGoals + awayGoals);
            result.setYellowCards(0);
            result.setSecondYellowCards(0);
            result.setDirectRedCards(0);

            // Build the Match entity with related entities loaded by ID
            Match match = new Match();
            match.setStatus(status);
            match.setDateVenue(dateVenue);
            match.setMatchGroup(matchGroup);
            match.setOriginCompetitionId(originCompetitionId);
            match.setOriginCompetitionName(originCompetitionName);
            match.setSeason(season);
            match.setStadium(stadium);

            // Set related entities using their repositories via services
            match.setHomeTeam(teamService.getTeamById(homeTeamId));
            match.setAwayTeam(teamService.getTeamById(awayTeamId));

            if (stageId != null && !stageId.isBlank()) {
                match.setStage(stageService.getStageById(stageId));
            }
            if (sportTypeId != null) {
                match.setSportType(sportService.getSportById(sportTypeId));
            }

            matchService.createMatch(match, result);
            redirectAttributes.addFlashAttribute("successMessage", "Event created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create event: " + e.getMessage());
        }

        return "redirect:/matches";
    }
}
