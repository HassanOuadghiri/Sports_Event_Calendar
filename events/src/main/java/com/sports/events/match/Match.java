package com.sports.events.match;

import com.sports.events.result.Result;
import com.sports.events.sport.SportType;
import com.sports.events.stage.Stage;
import com.sports.events.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "Status is required")
    @Column(name = "status")
    private String status;

    @NotNull(message = "Date and venue time is required")
    @Column(name = "date_venue")
    private LocalDateTime dateVenue;

    @Column(name = "match_group")
    private String matchGroup;

    @Column(name = "origin_competition_id")
    private String originCompetitionId;

    @Column(name = "origin_competition_name")
    private String originCompetitionName;

    @NotNull(message = "Season is required")
    @Column(name = "season")
    private Integer season;

    @NotNull(message = "Home team is required")
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @NotNull(message = "Away team is required")
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
