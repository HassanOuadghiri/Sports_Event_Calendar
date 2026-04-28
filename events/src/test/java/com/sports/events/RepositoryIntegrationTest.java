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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Repository Integration Tests")
class RepositoryIntegrationTest {

    @Autowired private MatchRepository matchRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private ResultRepository resultRepository;
    @Autowired private StageRepository stageRepository;
    @Autowired private SportRepository sportRepository;

    @Test
    @DisplayName("Team can be saved and retrieved")
    void teamRepository_saveAndFind() {
        Team team = new Team(null, "Brazil", "Brazil National Team", "brazil", "BRA", "BR", 1);
        Team saved = teamRepository.save(team);

        assertNotNull(saved.getId());
        assertEquals("Brazil", teamRepository.findById(saved.getId()).orElseThrow().getName());
    }

    @Test
    @DisplayName("Stage with String ID can be saved and retrieved")
    void stageRepository_saveAndFind() {
        Stage stage = new Stage("SF", "Semi Final", 4);
        stageRepository.save(stage);

        Stage found = stageRepository.findById("SF").orElseThrow();
        assertEquals("Semi Final", found.getName());
        assertEquals(4, found.getOrdering());
    }

    @Test
    @DisplayName("Result can be saved with validation constraints")
    void resultRepository_saveAndFind() {
        Result result = new Result(null, 3, 2, "HOME", "Great match", 5, 2, 0, 1);
        Result saved = resultRepository.save(result);

        assertNotNull(saved.getId());
        assertEquals(3, saved.getHomeGoals());
    }

    @Test
    @DisplayName("SportType can be saved and retrieved")
    void sportRepository_saveAndFind() {
        SportType sport = new SportType(null, "Tennis");
        SportType saved = sportRepository.save(sport);

        assertNotNull(saved.getId());
        assertEquals("Tennis", sportRepository.findById(saved.getId()).orElseThrow().getName());
    }

    @Test
    @DisplayName("Match with relationships can be saved and retrieved")
    void matchRepository_saveWithRelationships() {
        SportType sport = sportRepository.save(new SportType(null, "Football"));
        Stage stage = stageRepository.save(new Stage("FIN", "Final", 5));
        Team home = teamRepository.save(new Team(null, "Argentina", "Argentina NT", "argentina", "ARG", "AR", 1));
        Team away = teamRepository.save(new Team(null, "Italy", "Italy NT", "italy", "ITA", "IT", 2));
        Result result = resultRepository.save(new Result(null, 1, 0, "HOME", "Close game", 1, 3, 0, 0));

        Match match = new Match(null, "FINISHED", LocalDateTime.of(2026, 6, 15, 20, 0),
                null, "WC-001", "World Cup", 2026, home, away, result, stage, "Lusail Stadium", sport);
        Match saved = matchRepository.save(match);

        assertNotNull(saved.getId());
        Match found = matchRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Argentina", found.getHomeTeam().getName());
        assertEquals("Italy", found.getAwayTeam().getName());
        assertEquals("Final", found.getStage().getName());
    }

    @Test
    @DisplayName("MatchRepository custom query findByDateVenueBetween works")
    void matchRepository_findByDateRange() {
        SportType sport = sportRepository.save(new SportType(null, "Football"));
        Team t1 = teamRepository.save(new Team(null, "TeamA", "Team A", "teama", "TA", "AA", 1));
        Team t2 = teamRepository.save(new Team(null, "TeamB", "Team B", "teamb", "TB", "BB", 2));

        Match m1 = new Match(null, "FINISHED", LocalDateTime.of(2026, 5, 10, 18, 0),
                null, "CUP", "Cup", 2026, t1, t2, null, null, "Stadium", sport);
        Match m2 = new Match(null, "FINISHED", LocalDateTime.of(2026, 5, 11, 20, 0),
                null, "CUP", "Cup", 2026, t2, t1, null, null, "Stadium", sport);
        matchRepository.saveAll(List.of(m1, m2));

        LocalDateTime start = LocalDateTime.of(2026, 5, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 11, 0, 0);
        List<Match> found = matchRepository.findByDateVenueBetween(start, end);

        assertEquals(1, found.size());
        assertEquals("TeamA", found.get(0).getHomeTeam().getName());
    }
}
