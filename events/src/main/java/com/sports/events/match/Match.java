package com.sports.events.match;

import com.sports.events.result.Result;
import com.sports.events.sport.SportType;
import com.sports.events.stage.Stage;
import com.sports.events.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "date_venue")
    private LocalDateTime dateVenue;

    @Column(name = "match_group")
    private String matchGroup;

    @Column(name = "origin_competition_id")
    private String originCompetitionId;

    @Column(name = "origin_competition_name")
    private String originCompetitionName;

    @Column(name = "season")
    private Integer season;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @OneToOne
    @JoinColumn(name = "result_id", unique = true)
    private Result result;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Column(name = "stadium")
    private String stadium;

    @ManyToOne
    @JoinColumn(name = "sport_type_id")
    private SportType sportType;

}
