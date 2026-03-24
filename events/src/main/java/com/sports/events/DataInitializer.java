package com.sports.events;

import com.sports.events.match.Match;
import com.sports.events.match.MatchRepository;
import com.sports.events.result.Result;
import com.sports.events.result.ResultRepository;
import com.sports.events.sport.SportRepository;
import com.sports.events.sport.SportType;
import com.sports.events.stage.Stage;
import com.sports.events.stage.StageRepository;
import com.sports.events.team.Team;
import com.sports.events.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StageRepository stageRepository;
    private final TeamRepository teamRepository;
    private final ResultRepository resultRepository;
    private final MatchRepository matchRepository;
    private final SportRepository sportTypeRepository;

    @Override
    public void run(String... args) {

        // --- Sport Types ---
        SportType football = sportTypeRepository.save(new SportType(null, "Football"));
        SportType basketball = sportTypeRepository.save(new SportType(null, "Basketball"));

        // --- Stage ---
        Stage group = stageRepository.save(new Stage("GROUP", "Group Stage", 1));
        Stage r16 = stageRepository.save(new Stage("R16", "Round of 16", 2));
        Stage qf = stageRepository.save(new Stage("QF", "Quarter Final", 3));

        // --- Team ---
        Team portugal = teamRepository.save(new Team(null, "Portugal", "Portugal National Team", "portugal", "POR", "PT", 1));
        Team spain = teamRepository.save(new Team(null, "Spain", "Spain National Team", "spain", "ESP", "ES", 2));
        Team france = teamRepository.save(new Team(null, "France", "France National Team", "france", "FRA", "FR", 1));
        Team germany = teamRepository.save(new Team(null, "Germany", "Germany National Team", "germany", "GER", "DE", 2));

        // --- Result ---
        Result r1 = resultRepository.save(new Result(null, 2, 1, "HOME", "Portugal won in regular time", 3, 4, 0, 0));
        Result r2 = resultRepository.save(new Result(null, 1, 1, "DRAW", "Match ended in a draw", 2, 3, 0, 0));
        Result r3 = resultRepository.save(new Result(null, 0, 3, "AWAY", "Germany dominated the match", 3, 2, 0, 1));

        // --- Matches ---
        matchRepository.save(new Match(
                        null,
                        "FINISHED",
                        LocalDateTime.of(2026, 3, 20, 18, 30),
                        "A",
                        "UCL-001",
                        "International Cup",
                        2026,
                        portugal,
                        spain,
                        r1,
                        group,
                "National Stadium",
                football
                ));

        matchRepository.save(new Match(
                        null,
                        "FINISHED",
                        LocalDateTime.of(2026, 3, 21, 21, 0),
                        "B",
                        "UCL-001",
                        "International Cup",
                        2026,
                        france,
                        germany,
                        r2,
                        group,
                "City Arena",
                football
                ));

        matchRepository.save(new Match(
                        null,
                        "FINISHED",
                        LocalDateTime.of(2026, 3, 22, 16, 0),
                        null,
                        "UCL-001",
                        "International Cup",
                        2026,
                        spain,
                        germany,
                        r3,
                        r16,
                "Olympic Park",
                basketball
                ));
    }
}